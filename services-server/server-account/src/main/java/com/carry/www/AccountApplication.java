package com.carry.www;

import com.carry.www.seata.config.DataSourceProxyAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableHystrix
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.carry.www.account.mapper"})
@ComponentScan(basePackages = "com.carry.*")//注解util类
@Import(DataSourceProxyAutoConfiguration.class)
public class AccountApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("###################### AccountApplication 服务启动完成！######################");

    }

}
