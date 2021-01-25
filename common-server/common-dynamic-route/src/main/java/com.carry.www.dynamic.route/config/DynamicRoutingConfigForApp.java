package com.carry.www.dynamic.route.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.carry.www.dynamic.route.entity.FilterEntity;
import com.carry.www.dynamic.route.entity.PredicateEntity;
import com.carry.www.dynamic.route.entity.RouteEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.PredicatedList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * 从Nacos获取动态路由
 * 实现ApplicationEventPublisherAware发布接口来发布路由更新事件
 */
@Configuration
@Component
@Slf4j
public class DynamicRoutingConfigForApp implements ApplicationEventPublisherAware {

    @Value("${dynamic.route.server-addr}")
    private String serverAddr;

    @Value("${nacos.namespace}")
    private String namespace;

    @Value("${dynamic.route.data-id}")
    private String dataId;

    @Value("${dynamic.route.group}")
    private String groupId;

    // 保存、删除路由服务
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * @return
     * @Author carryer
     * @Description 获取nacos配置服务
     * @Date
     * @Param
     **/
    public ConfigService getNacosConfigInfo() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);

        return configService;
    }


    /**
     * @return
     * @Author carryer
     * @Description 刷新路由
     * @Date
     * @Param
     **/
    public void refreshRouting() throws IOException {
        String resultContent = "";
        //http://xxx.xxx.xxx.xxx:8848/nacos/v1/ns/catalog/services?hasIpCount=true&withInstances=false&pageNo=1&pageSize=10
        String url = "http://" + this.serverAddr + "/nacos/v1/ns/catalog/services?hasIpCount=true&withInstances=false&pageNo=1&pageSize=1000";
        url = url+"&namespaceId=dev";
        List<String> params = new LinkedList<>();

        SelfHttp.HttpResult result = SelfHttp.doGet(url, "UTF-8", params);
        if (result.code == HttpURLConnection.HTTP_OK) {
            resultContent = result.content;
        }
        System.out.println(resultContent);
        if (StringUtils.isNotBlank(resultContent)) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(resultContent);
            List<Map> listMap = (List<Map>) jsonObject.get("serviceList");
            List<RouteEntity> list = new ArrayList<>();
            for (Map m : listMap) {
                String appName = String.valueOf(m.get("name"));
                RouteEntity routeEntity = new RouteEntity();
                routeEntity.setId(appName);
                routeEntity.setUri("lb://" + appName);

                List<FilterEntity> filters = new ArrayList<>();
                FilterEntity filterEntity = new FilterEntity();
                Map<String, String> args = new HashMap<>();
                args.put("parts", "1");
                filterEntity.setName("StripPrefix");
                filterEntity.setArgs(args);
                routeEntity.setFilters(filters);

                PredicateEntity predicateEntity = new PredicateEntity();
                predicateEntity.setName("Path");
                args.clear();
                args = new HashMap<>();
                args.put("pattern", "/" + appName + "/**");
                predicateEntity.setArgs(args);
                List<PredicateEntity> predicates = new ArrayList<>();
                predicates.add(predicateEntity);
                routeEntity.setPredicates(predicates);

                list.add(routeEntity);
            }

            //获取路由集合
            try {
                //更新路由表
                list.stream().limit(1).forEach(x -> {
                    try {
                        update(assembleRouteDefinition(x));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (
                    Exception e) {

            }
        }
    }


    /**
     * @return
     * @Author carryer
     * @Description 路由更新
     * @Date
     * @Param
     **/
    private void update(RouteDefinition routeDefinition) throws Exception {
        //先删除路由
        routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        //再保存路由
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        //发布事件 发布者是RefreshRoutesEvent  事件是刷新路由
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * @return
     * @Author carryer
     * @Description 实体信息解析
     * @Date
     * @Param
     **/
    private RouteDefinition assembleRouteDefinition(RouteEntity routeEntity) {
        RouteDefinition definition = new RouteDefinition();
        // ID
        definition.setId(routeEntity.getId());

        // Predicates断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        for (PredicateEntity predicateEntity : routeEntity.getPredicates()) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setArgs(predicateEntity.getArgs());
            predicateDefinition.setName(predicateEntity.getName());
            pdList.add(predicateDefinition);
        }
        definition.setPredicates(pdList);

        // Filters过滤器
        List<FilterDefinition> fdList = new ArrayList<>();
        for (FilterEntity filterEntity : routeEntity.getFilters()) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setArgs(filterEntity.getArgs());
            filterDefinition.setName(filterEntity.getName());
            fdList.add(filterDefinition);
        }
        definition.setFilters(fdList);

        // URI
        URI uri = UriComponentsBuilder.fromUriString(routeEntity.getUri()).build().toUri();
        definition.setUri(uri);

        return definition;
    }
}

