package com.carry.www;

import com.carry.www.lock.api.RedisLocker;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@MapperScan(basePackages = {"com.carry.www.order.mapper"})
@ComponentScan(basePackages = "com.carry.*")//注解base-util依赖类
@EnableScheduling
@EnableAsync
public class GoodsApplcation implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplcation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.opsForValue().set("test-lock", "10");


        System.out.println("###################### GoodsApplcation 服务启动完成！######################");

    }

    @Bean
    public Redisson RedissonConnect() {
        //Config config=new Config();
        //config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);

        return (Redisson) Redisson.create();
    }

    @Bean
    public RedisLocker redisLocker() {
        //Config config=new Config();
        //config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        Redisson redisson = (Redisson) Redisson.create();
        return new RedisLocker(redisson);
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
