package cn.tyrone.springboot.dubbo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import cn.tyrone.springboot.dubbo.api.DemoService;

@EnableDubbo	// @EnableDubbo <==> @EnableDubboConfig + @DubboComponentScan
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	/*
	 * 通过 @Reference 注解，生成远程代理服务，用于获取服务端请求
	 */
	@Reference(interfaceClass = DemoService.class)
	private DemoService demoService;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		String name = "SpringBoot-Dubbo-Consummer-Example";
		
		String result = demoService.sayHello(name);
		
		log.info("SpringBoot整合Dubbo消费端示例结果：" + result);
		
	}

}
