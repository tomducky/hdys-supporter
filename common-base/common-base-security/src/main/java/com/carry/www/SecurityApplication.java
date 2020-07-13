package com.carry.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringCloudApplication
@EnableWebSecurity
@MapperScan(basePackages = { "com.carry.www.*" })
public class SecurityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("######################security服务启动完成！######################");
	}

//	/**
//	 * @方法描述: 负载均衡用服务名
//	 * @return: org.springframework.web.client.RestTemplate
//	 * @Author: carry
//	 */
//	@Bean
//	@LoadBalanced
//	RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
//
//	/**
//	 * @方法描述: ip调用，不要开启负载均衡
//	 * @return: org.springframework.web.client.RestTemplate
//	 * @Author: carry
//	 */
//	@Bean(name="restTemplateOfIp")
//	RestTemplate restTemplateOfIp() {
//		return new RestTemplate();
//	}
}
