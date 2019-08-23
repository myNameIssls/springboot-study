package cn.tyrone.springboot.integrate.activemq;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class Producer {

    /*
     * Jms消息模板
     */
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /*
     * 队列
     */
    @Autowired
    private Queue queue;

    /*
     * 发送消息
     */
    public void send(String msg) {
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }
}
