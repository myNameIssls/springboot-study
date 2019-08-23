package cn.tyrone.springboot.rabbitmq.consumer;

import cn.tyrone.springboot.rabbitmq.beans.RabbitObject;
import cn.tyrone.springboot.rabbitmq.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费端
 */
@Component
public class RabbitMQConsumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = {Constant.QUEUE_NAME_TEST})
    public void queueTestConsumer(RabbitObject rabbitObject){
        logger.info("-------- 消息-id:{}", rabbitObject.toString());
    }

//    @RabbitListener(queues = {Constant.QUEUE_NAME_TEST})
//    public void queueTestConsumer(Message message){
//        logger.info("-------- 消息-id:{}", message.getBody());
//    }


}
