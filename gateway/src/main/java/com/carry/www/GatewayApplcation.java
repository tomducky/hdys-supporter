package com.carry.www;

import com.carry.www.dynamic.route.config.DynamicRoutingConfigForApp;
import com.carry.www.log.mapper.AopMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableHystrix
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.carry.www.*")//扫描util等基础类
@MapperScan(basePackages = { "com.carry.www.log.mapper.*" })
public class GatewayApplcation implements CommandLineRunner {
    @Autowired
    DynamicRoutingConfigForApp dynamicRoutingConfigForApp;
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplcation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### gateway 服务启动完成！######################");
        System.out.println("###################### 自动注册路由 START！######################");
        dynamicRoutingConfigForApp.refreshRouting();
        System.out.println("###################### 自动注册路由 END！######################");
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * @方法描述: ip调用，不要开启负载均衡
     * @return: org.springframework.web.client.RestTemplate
     * @Author: carry
     */
    @Bean(name = "restTemplateOfIp")
    RestTemplate restTemplateOfIp() {
        return new RestTemplate();
    }
}
