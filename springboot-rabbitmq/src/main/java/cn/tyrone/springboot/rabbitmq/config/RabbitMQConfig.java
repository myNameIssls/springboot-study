package cn.tyrone.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 消息转化器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Test 交换机
     * @return
     */
    @Bean
    public DirectExchange exchangeTest(){
        DirectExchange directExchange = new DirectExchange(Constant.EXCHANGE_NAME_TEST);
        return directExchange;
    }

    /**
     * test 队列
     * @return
     */
    @Bean
    public Queue queueTest(){
        Queue queueTest = new Queue(Constant.QUEUE_NAME_TEST);
        return queueTest;
    }

    /**
     * 增加绑定关系
     */
    @Bean
    public Binding bindingExchangeTest2QueueTest(){
        Binding binding = BindingBuilder.bind(this.queueTest()).to(this.exchangeTest()).with(Constant.QUEUE_NAME_TEST);
        return binding;
    }
}
