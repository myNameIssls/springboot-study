package cn.tyrone.springboot.integrate.activemq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Application implements CommandLineRunner {
	
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}
	
	@Autowired private Producer Producer;
	
	@Override
	public void run(String... args) throws Exception {
		Producer.send("Hello! SpringBoot integrate Apache ActiveMQ!!!");
		System.out.println("发送消息结束！！！");
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	

}
