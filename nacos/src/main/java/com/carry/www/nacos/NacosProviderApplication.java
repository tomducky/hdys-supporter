package com.carry.www.nacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/7/13 11:18
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### nacos 服务启动完成！######################");
    }
}
