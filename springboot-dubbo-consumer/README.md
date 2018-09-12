# springboot-dubbo-consumer

## springboot-dubbo-consumer 工程概述
springboot-dubbo-consumer是基于SpringBoot集成Dubbo示例程序的消费端程序，本示例程序过com.alibaba.dubbo.config.annotation.Reference注解来引用服务端暴露的服务接口

## 实现步骤分析
### 引入相关依赖

```
<dependencies>
	<!-- 引入springboot相关 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>

	<!-- 引入springboot dubbo依赖 -->
	<dependency>
		<groupId>com.alibaba.boot</groupId>
		<artifactId>dubbo-spring-boot-starter</artifactId>
		<version>0.2.0</version>
	</dependency>
	
	<!-- 引入RPC公共接口 -->
	<dependency>
		<groupId>cn.tyrone.springboot</groupId>
		<artifactId>springboot-dubbo-api</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
</dependencies>
```
注意：`springboot-dubbo-api`属于dubbo要暴露的服务接口工程


### 创建application.yml文件
```
server:
  port: 7070
spring:
  application:
    name: spring-boot-consumer
  
dubbo:
  application:
    id: spring-boot-consumer
    name: spring-boot-consumer
  registry:
    protocol: zookeeper
    address: localhost:2181
```

### 创建springboot-dubbo-consumer启动类
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
```


博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[SpringBoot 2.0 集成 Dubbo](https://blog.csdn.net/myNameIssls/article/details/82669224)

参考链接:  <br>
[https://github.com/apache/incubator-dubbo](https://github.com/apache/incubator-dubbo) <br>
[https://github.com/apache/incubator-dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples](https://github.com/apache/incubator-dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples) <br>











