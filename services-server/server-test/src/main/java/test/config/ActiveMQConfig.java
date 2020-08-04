//package test.config;
//
//import org.apache.activemq.command.ActiveMQQueue;
//import org.apache.activemq.command.ActiveMQTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.config.JmsListenerContainerFactory;
//import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.jms.ConnectionFactory;
//import javax.jms.Queue;
//import javax.jms.Topic;
//
///**
// * 类描述：
// *
// * @author ：carry
// * @version: 1.0  CreatedDate in  2020/7/29 17:10
// * <p>
// * 修订历史： 日期			修订者		修订描述
// */
//@Component
//@Configuration
//@ConfigurationProperties(prefix = "spring")
//public class ActiveMQConfig {
//    @Value("${spring.activemq.queue-name}")
//    public String queueName;
//
//    @Value("${spring.activemq.topic-name}")
//    public String topicName;
//
//    /**
//     * @方法描述: queue服务  可在nacos配置文件配置
//     * @Param: []
//     * @return: javax.jms.Queue
//     * @Author: carry
//     */
//    @Bean
//    public Queue queue() {
//        return new ActiveMQQueue(queueName);
//    }
//
//    /**
//     * @方法描述: topic服务 可在nacos配置文件配置
//     * @Param: []
//     * @return: javax.jms.Topic
//     * @Author: carry
//     */
//    @Bean
//    public Topic topic() {
//        return new ActiveMQTopic(topicName);
//    }
//
//
//    /**
//     * @方法描述:  P2P监听器
//     * @Param: [connectionFactory]
//     * @return: org.springframework.jms.config.JmsListenerContainerFactory<?>
//     * @Author: carry
//     */
//    @Bean("queueListener")
//    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(false);
//        return factory;
//    }
//
//    /**
//     * @方法描述:   PUB/SUP监听器
//     * @Param: [connectionFactory]
//     * @return: org.springframework.jms.config.JmsListenerContainerFactory<?>
//     * @Author: carry
//     */
//    @Bean("topicListener")
//    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(true);
//        return factory;
//    }
//}
