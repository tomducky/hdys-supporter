package test.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 类描述：生产者
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/7/29 16:23
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @Autowired
    private Queue queue;

    @PostMapping("/sendQueue")
    public void sendQueue(@RequestBody String msg) {
        this.sendMessage(this.queue, msg);
    }

    @PostMapping("/sendTopic")
    public void sendTopic(@RequestBody String msg) {
        this.sendMessage(this.topic, msg);
    }

    // destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

}
