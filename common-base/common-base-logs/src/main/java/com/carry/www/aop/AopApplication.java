package com.carry.www.aop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableConfigurationProperties//使使用 @ConfigurationProperties 注解的类生效。
@SpringCloudApplication
@MapperScan(basePackages = {"com.carry.www.aop.log.mapper"})
public class AopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("######################  logs 服务启动完成！######################");
    }
}
