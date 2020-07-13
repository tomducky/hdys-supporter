package com.carry.www.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringCloudApplication
@EnableConfigurationProperties
@MapperScan(basePackages = { "com.carry.www.system.mapper" })
public class SystemApplcation implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplcation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### SystemApplcation 服务启动完成！######################");
    }
}
