package cn.tyrone.springboot.redis.message;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

//https://spring.io/guides/gs/messaging-redis/

@SpringBootApplication
public class Application {
	public static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	/*
	 * Redis消息监听器容器
	 * 这个容器加载了RedisConnectionFactory和消息监听器
	 */
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, 
			MessageListenerAdapter listenerAdapter){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("sprinboot-redis-messaage"));
		return container;
	}
	
	/*
	 * 将Receiver注册为一个消息监听器，并指定消息接收的方法（receiveMessage）
	 * 如果不指定消息接收的方法，消息监听器会默认的寻找Receiver中的handleMessage这个方法作为消息接收的方法
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver){
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	/*
	 * Receiver实例
	 */
	@Bean
	Receiver receiver(CountDownLatch latch){
		return new Receiver(latch);
	}
	
	@Bean
	CountDownLatch latch(){
		return new CountDownLatch(1);
	}
	
	/*
	 * Redis Template 用来发送消息
	 */
	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory){
		return new StringRedisTemplate(connectionFactory);
	}
	
	/*
	 * 测试用例
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
		
		LOGGER.info("Sending message......");
		template.convertAndSend("sprinboot-redis-messaage", "Hello, SpringBoot redis message!!!!");
		latch.wait();
		
		System.exit(0);
		
	}
	
}
