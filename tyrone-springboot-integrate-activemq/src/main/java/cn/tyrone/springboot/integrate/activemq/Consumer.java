package cn.tyrone.springboot.integrate.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	@JmsListener(destination = "sample.queue")
	public void receiveMessge(String text){
		System.out.println("----接收消息开始 ----");
		System.out.println("接收的消息内容:\t" + text);
		System.out.println("----接收消息结束 ----");
	}
}
