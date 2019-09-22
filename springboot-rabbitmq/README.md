
# springboot-rabbitmq

## springboot-rabbitmq 工程概述
`springboot-rabbitmq` 是SpringBoot与RabbitMQ整合的示例工程

## 实现步骤分析

### 添加依赖
```
<!-- SpringBoot RabbitMQ Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<!-- 处理MQ队列消息绑定依赖 -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

```

### `RabbitMQ 连接配置`

```
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 添加`RabbitMQ`常量配置
本例将 `RabbitMQ` 交换机名称、队列名称、路由名称放到一个常量类中维护，便于消息生产端与消费端共用。示例代码如下：

```
public class Constant {

    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME_TEST = "test-exchange";

    /**
     * 声明一个队列名称
     */
    public static final String QUEUE_NAME_TEST = "test-queue";

}

```

### `RabbitMQ`交换机、队列、绑定关系配置
```
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

```

### 数据对象创建
本例以该类作为需要处理的 MQ 消息
```
import java.io.Serializable;

public class RabbitObject implements Serializable {

    private String id;

    private String messge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    @Override
    public String toString() {
        return "RabbitObject{" +
                "id='" + id + '\'' +
                ", messge='" + messge + '\'' +
                '}';
    }
}
```

### 主启动类创建
主启动类主要用于启动 `SpringBoot RabbitMQ`工程，并通过实现`CommandLineRunner`接口来实现队列消息的发送。示例代码如下：
```
import cn.tyrone.springboot.rabbitmq.beans.RabbitObject;
import cn.tyrone.springboot.rabbitmq.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@EnableRabbit
@SpringBootApplication
public class RabbitMQApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("------- 启动成功 ----------");

        RabbitObject rabbitObject = new RabbitObject();
        rabbitObject.setId(UUID.randomUUID().toString());
        rabbitObject.setMessge("消息:" + UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(Constant.EXCHANGE_NAME_TEST, Constant.QUEUE_NAME_TEST, rabbitObject);

        logger.info("------- 发送消息成功 ----------");

    }
}
```

### 消息消费端创建
```
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

}
```

因为在 `RabbitMQConfig`类中配置了消息的转化器，所以消费端可以直接以对象的形势来接收。

### 测试
启动主启动类类，并观察控制台日志输出，如下：
```
2019-09-01 17:42:26.124  INFO 31802 --- [           main] ication$$EnhancerBySpringCGLIB$$6dde41ae : ------- 启动成功 ----------
2019-09-01 17:42:26.181  INFO 31802 --- [           main] ication$$EnhancerBySpringCGLIB$$6dde41ae : ------- 发送消息成功 ----------
2019-09-01 17:42:26.241  INFO 31802 --- [ntContainer#0-1] c.t.s.r.consumer.RabbitMQConsumer        : -------- 消息-id:RabbitObject{id='936f6477-0193-4367-9749-1279baf3685c', messge='消息:792c957a-ea35-40f1-a960-783fa5d3dedf'}

```

## SpringBoot RabbitMQ ACK 机制

### ACK 机制流程图
<img src="https://github.com/myNameIssls/springboot-study/blob/master/docs/images/RabbitMQ-Ack.jpg"><br/>

### application.yml 配置

```
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    # publisher-confirms和publisher-returns是对于消息生产端的配置
    publisher-confirms: true # 开启发送消息确认 对应RabbitTemplate.ConfirmCallback接口
    publisher-returns: true  # 开启发送消息失败返回 对应RabbitTemplate.ReturnCallback接口

    # 这个配置是针对消息消费端的配置
    listener:
      direct:
        acknowledge-mode: manual # 开启 ack 手动确认
      simple:
        acknowledge-mode: manual # 开启 ack 手动确认
```

### 消息生产端发送确认配置

```
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
```

### 消息消费关消费消息确认

```
@RabbitListener(queues = {Constant.QUEUE_NAME_TEST})
public void queueTestConsumer(RabbitObject rabbitObject, Channel channel, Message message){

    String msg = new String(message.getBody());

    try {

        // 当前线程休眠5秒钟，模拟消息消费过程
        Thread.sleep(5000);
        // 正常消费消息
        logger.info("-------- 消息-id:{}", rabbitObject.toString());

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        logger.error("消息[{}]确认处理成功", msg);


    } catch (Exception e) {

        // redelivered = true, 表明该消息是重复处理消息
        Boolean redelivered = message.getMessageProperties().getRedelivered();

        /**
         * 这里对消息重入队列做设置，例如将消息序列化缓存至 Redis, 并记录重入队列次数
         * 如果该消息重入队列次数达到一次次数，比如3次，将不再重入队列，直接拒绝
         * 这时候需要对消息做补偿机制处理
         *
         * channel.basicNack与channel.basicReject要结合越来使用
         *
         */
        try {

            if (redelivered) {

                /**
                 * 1. 对于重复处理的队列消息做补偿机制处理
                 * 2. 从队列中移除该消息，防止队列阻塞
                 */
                // 消息已重复处理失败, 扔掉消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
                logger.error("消息[{}]重新处理失败，扔掉消息", msg);
            }

            // redelivered != true,表明该消息是第一次消费
            if (!redelivered) {

                // 消息重新放回队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                logger.error("消息[{}]处理失败，重新放回队列", msg);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }


        e.printStackTrace();
    }
}
```

- channel.basicAck: 表示消息正常消费
- channel.basicNack: 消息消费异常，将消息重新往往队列
- channel.basicReject: 消息消费异常，拒绝该消息入队列


博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[基于 SpringBoot 集成 RabbitMQ](https://blog.csdn.net/myNameIssls/article/details/100185729) <br />
[SpringBoot RabbitMQ ACK 机制](https://blog.csdn.net/myNameIssls/article/details/101163617)








