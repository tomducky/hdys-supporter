package test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJms
public class TestApplcation implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestApplcation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### TEST 服务启动完成！######################");
    }

    /**
     * @方法描述: queue服务  可在nacos配置文件配置
     * @Param: []
     * @return: javax.jms.Queue
     * @Author: carry
     */
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("hdys-queue");
    }

    /**
     * @方法描述: topic服务 可在nacos配置文件配置
     * @Param: []
     * @return: javax.jms.Topic
     * @Author: carry
     */
    @Bean
    public Topic topic() {
        return new ActiveMQTopic("hdys-topic");
    }


    /**
     * @方法描述:  P2P监听器
     * @Param: [connectionFactory]
     * @return: org.springframework.jms.config.JmsListenerContainerFactory<?>
     * @Author: carry
     */
    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    /**
     * @方法描述:   PUB/SUP监听器
     * @Param: [connectionFactory]
     * @return: org.springframework.jms.config.JmsListenerContainerFactory<?>
     * @Author: carry
     */
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
