package com.carry.www.utils.base;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import net.sf.json.JSONObject;

/**
 * 类描述：RestTemplate封装  服务间相互调用类
 * restTemplate 内部服务相互调用  restTemplateOfIp调用外部服务
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年10月09日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Slf4j
@Component
public class BaseRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplateOfIp")
    RestTemplate restTemplateOfIp;

    /**
     * @方法描述: 服务相互调用
     * @return: url服务url  requestBody 请求的参数 mediaType 接收的类型  responseBodyType返回值类型 httpMethod 请求类型 GET或POST
     * type 0内部调用  1外部调用
     * @Author: carry
     */
    public AjaxResult fallbackOfApiToApi(String url, String httpMethod, MediaType mediaType, Object requestBody, Object responseBodyType, String type, AuthUser authUser) {

        return AjaxResult.error("获取数据失败");
    }


    /**
     * @方法描述: 服务相互调用
     * @return: url服务url  httpMethod请求类型 GET或POST 后台接收的类型默认JSON   requestBody 请求的参数 mediaType  responseBodyType返回值类型
     * type 0内部调用  1外部调用 authUser用户信息 解决其他地方获取不到用户错误
     * @Author: carry
     */
    @HystrixCommand(fallbackMethod = "fallbackOfApiToApi", commandProperties = {@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "10000"), @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "10000")})
    public AjaxResult apiToApi(String url, String httpMethod, MediaType mediaType, Object requestBody, Object responseBodyType, String type, AuthUser authUser) {
        HttpHeaders headers = new HttpHeaders();
        Object returnType = null;

        log.error("################## apiToApi START##########################");

        if (StringUtils.isEmpty(url)) {
            return AjaxResult.error("请求地址必传！");
        }

        if (StringUtils.isEmpty(httpMethod)) {
            return AjaxResult.error("请求方法类型必传！");
        }

        if (null == responseBodyType) {
            return AjaxResult.error("返回值类型必传！");
        }

        if (StringUtils.isEmpty(type)) {
            return AjaxResult.error("内部外部调用标记flag必传！");
        }

        log.error("#### url=" + url + "#### httpMethod=" + httpMethod + "#### mediaType=" + mediaType + "#### requestBody=" + requestBody + "#### responseBodyType=" + responseBodyType + "#### type=" + type);

        try {
            if ("0".equals(type)) {
                // 内部调用 拼接Authorization token
                String token = "";
                if (null != authUser) {
                    token = authUser.getTokenId();
                }

                headers.add("Authorization", "Bearer " + token);
            }

            //默认JSON
            headers.setContentType(mediaType == null ? MediaType.APPLICATION_JSON : mediaType);

            if ("GET".equals(httpMethod)) {
                HttpEntity<Object> request = new HttpEntity<Object>(headers);
                ResponseEntity<?> resEntity = null;

                if ("0".equals(type)) {
                    resEntity = restTemplate.exchange(url, HttpMethod.GET, request, responseBodyType.getClass());
                } else {
                    resEntity = restTemplateOfIp.exchange(url, HttpMethod.GET, request, responseBodyType.getClass());
                }

                returnType = resEntity.getBody();
            } else if ("POST".equals(httpMethod)) {
                HttpEntity<Object> request = new HttpEntity<Object>(requestBody, headers);

                if ("0".equals(type)) {
                    returnType = restTemplate.postForObject(url, request, responseBodyType.getClass());
                } else {

                    returnType = restTemplateOfIp.postForObject(url, request, responseBodyType.getClass());
                }

            } else {

                return AjaxResult.error("不支持的请求类型");
            }

            JSONObject jsonObject = JSONObject.fromObject(returnType);

            log.error("################## apiToApi  END##########################");

            return AjaxResult.successInfo("获取数据成功", jsonObject);
        } catch (Exception e) {

            return AjaxResult.error("获取数据失败:" + e.getMessage());
        }
    }


}
