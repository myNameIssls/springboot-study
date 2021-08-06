package cn.tyrone.springboot.rabbitmq;

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
        rabbitObject.setMessge("消息内容:" + UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(Constant.EXCHANGE_NAME_TEST, Constant.QUEUE_NAME_TEST, rabbitObject);

        logger.info("------- 发送消息成功 ----------");

    }
}
