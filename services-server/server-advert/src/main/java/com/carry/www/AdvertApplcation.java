package com.carry.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
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
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//如果子依赖用到了数据库，此服务未用到，需要在此去除
@EnableHystrix
@EnableDiscoveryClient
@EnableConfigurationProperties
@MapperScan(basePackages = { "com.carry.www.advert.mapper" })
@ComponentScan(basePackages = "com.carry.*")//注解base-util依赖类
public class AdvertApplcation implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AdvertApplcation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### AdvertApplcation 服务启动完成！######################");
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
