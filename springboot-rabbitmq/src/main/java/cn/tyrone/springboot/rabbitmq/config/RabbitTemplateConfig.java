package cn.tyrone.springboot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            // 指定 ConfirmCallback
        rabbitTemplate.setReturnCallback(this);             // 指定 ReturnCallback
    }


    /**
     * 如果消息到达 exchange, 则 confirm 回调, ack = true
     * 如果消息不到达 exchange, 则 confirm 回调, ack = false
     * 需要设置spring.rabbitmq.publisher-confirms=true
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        logger.info("消息是否到达Exchange:{}", ack == true ? "消息成功到达Exchange" : "消息到达Exchange失败");
        if (!ack) {
            logger.info("消息到达Exchange失败原因:{}", cause);

            // 根据业务逻辑实现消息补偿机制

        }

    }

    /**
     * exchange 到达 queue, 则 returnedMessage 不回调
     * exchange 到达 queue 失败, 则 returnedMessage 回调
     * 需要设置spring.rabbitmq.publisher-returns=true
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息报文:{}", new String(message.getBody()));
        logger.info("消息编号:{}", replyCode);
        logger.info("描述:{}", replyText);
        logger.info("交换机名称:{}", exchange);
        logger.info("路由名称:{}", routingKey);

        // 根据业务逻辑实现消息补偿机制

    }
}
