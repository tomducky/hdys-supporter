package com.carry.www.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.carry.www.dynamic.route.entity.FilterEntity;
import com.carry.www.dynamic.route.entity.PredicateEntity;
import com.carry.www.dynamic.route.entity.RouteEntity;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 从Nacos获取动态路由
 * 实现ApplicationEventPublisherAware发布接口来发布路由更新事件
 */
@Configuration
@Slf4j
public class DynamicRoutingConfig implements ApplicationEventPublisherAware {

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
//        properties.setProperty(PropertyKeyConst.USERNAME, username);
//        properties.setProperty(PropertyKeyConst.PASSWORD, password);
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);

        return configService;
    }

    /**
     * @return
     * @Author carryer
     * @Description 初始化路由
     * @Date
     * @Param
     **/
    @Bean
    public void initRouting() {
        try {
            ConfigService configService = this.getNacosConfigInfo();
            String configInfo = configService.getConfig(dataId, groupId, 5000);
            if (null != configInfo) {
                List<RouteEntity> list = JSONObject.parseArray(configInfo).toJavaList(RouteEntity.class);
                for (RouteEntity route : list) {
                    update(assembleRouteDefinition(route));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @Author carryer
     * @Description 刷新路由
     * @Date
     * @Param
     **/
    @Bean
    public void refreshRouting() {
        try {
            ConfigService configService = this.getNacosConfigInfo();
            //监听路由变化
            configService.addListener(
                    dataId,
                    groupId,
                    new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            System.out.println("###############33刷新路由#######################");
                            try {
                                log.info(configInfo);
                                if (null != configInfo) {
                                    List<RouteEntity> list =
                                            JSONObject.parseArray(configInfo).toJavaList(RouteEntity.class);
                                    //更新路由表
                                    for (RouteEntity route : list) {
                                        update(assembleRouteDefinition(route));
                                    }
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
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
