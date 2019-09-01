
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

//    @RabbitListener(queues = {Constant.QUEUE_NAME_TEST})
//    public void queueTestConsumer(Message message){
//        logger.info("-------- 消息-id:{}", message.getBody());
//    }


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


博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[基于 SpringBoot 集成 RabbitMQ](https://blog.csdn.net/myNameIssls/article/details/100185729)








