package com.carry.www.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication{

        public static void main(String[] args) {
            SpringApplication.run(EurekaApplication.class, args);
        }

}
