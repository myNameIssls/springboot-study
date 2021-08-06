package cn.tyrone.springboot.rabbitmq.consumer;

import cn.tyrone.springboot.rabbitmq.beans.RabbitObject;
import cn.tyrone.springboot.rabbitmq.config.Constant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息消费端
 */
@Component
public class RabbitMQConsumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @RabbitListener(queues = {Constant.QUEUE_NAME_TEST})
    public void queueTestConsumer(Message message){
        byte[] body = message.getBody();

        logger.info("-------- 消息-id:{}", message.getBody());

        String s = new String(body);

        logger.info("消息内容：{}", s);
    }


}
