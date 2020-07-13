package com.carry.www.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/6/15 11:13
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringCloudApplication
public class AuthApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### authCenter 服务启动完成！######################");
    }
    /**
     * @方法描述: 负载均衡用服务名
     * @return: org.springframework.web.client.RestTemplate
     * @Author: carry
     */

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
    @Bean(name="restTemplateOfIp")
    RestTemplate restTemplateOfIp() {
        return new RestTemplate();
    }
}
