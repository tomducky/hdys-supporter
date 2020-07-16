package com.carry.www.thread;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 类描述：
 * 线程池配置
 * 如果一个配置类只配置@ConfigurationProperties注解，而没有使用@Component，那么在IOC容器中是获取不到properties 配置文件转化的bean。
 * 在启动类中@EnableConfigurationProperties 相当于把使用 @ConfigurationProperties 的类进行了一次注入。
 * prefix=threadpool 表示yml配置文件下的threadpool所有顺序进行一一的映射
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月13日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Configuration
@Data
//@Component
@ConfigurationProperties(prefix = "threadpool")
public class ExecutePoolConfiguration {

    private static Logger logger = LoggerFactory.getLogger(ExecutePoolConfiguration.class);
//
//    @Value("${threadpool.core-pool-size}")
//    private String corePoolSize;
//
//    @Value("${threadpool.max-pool-size}")
//    private String maxPoolSize;
//
//    @Value("${threadpool.queue-capacity}")
//    private String queueCapacity;
//
//    @Value("${threadpool.keep-alive-seconds}}")
//    private String keepAliveSeconds;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    /*    pool.setKeepAliveSeconds(Integer.valueOf(keepAliveSeconds));
        pool.setCorePoolSize(Integer.valueOf(corePoolSize));//核心线程池数
        pool.setMaxPoolSize(Integer.valueOf(maxPoolSize)); // 最大线程
        pool.setQueueCapacity(Integer.valueOf(queueCapacity));//队列容量*/

        pool.setKeepAliveSeconds(10);
        pool.setCorePoolSize(20);//核心线程池数
        pool.setMaxPoolSize(1000); // 最大线程
        pool.setQueueCapacity(200);//队列容量
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); //队列满，线程被拒绝执行策略
        return pool;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
