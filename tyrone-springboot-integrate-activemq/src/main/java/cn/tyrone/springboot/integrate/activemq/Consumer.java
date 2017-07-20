package cn.tyrone.springboot.integrate.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	@JmsListener(destination = "sample.queue")
	public void receiveMessge(String text){
		System.out.println("发送的消息:\t" + text);
	}
}
