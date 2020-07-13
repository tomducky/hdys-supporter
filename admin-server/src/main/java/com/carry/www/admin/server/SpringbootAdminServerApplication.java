package com.carry.www.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年12月02日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringCloudApplication
@EnableAdminServer
public class SpringbootAdminServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdminServerApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("######################监控服务启动完成！######################");
    }
}