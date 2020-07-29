package test.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

/**
 * 类描述：
 *  消费者
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/7/29 16:29
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class Consumer {

    @JmsListener(destination = "hdys-queue")
    public void receiveQueue(String msg) {
        System.out.println("我是P2P消费者,收到的信息"+msg);
    }

    @JmsListener(destination = "hdys-topic", containerFactory="topicListener")
    public void receiveTopic(String msg) {
        System.out.println("我是PUB/SUB消费者,收到的信息"+msg);
    }

}
