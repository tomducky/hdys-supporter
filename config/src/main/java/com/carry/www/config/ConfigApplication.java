package com.carry.www.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年05月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@EnableConfigServer
@SpringCloudApplication
public class ConfigApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### config 服务启动完成！######################");
    }
}
