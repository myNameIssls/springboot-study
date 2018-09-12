# springboot-dubbo-provider

## springboot-dubbo-provider 工程概述
springboot-dubbo-provider是基于SpringBoot集成Dubbo示例程序的服务端程序，本示例程序过com.alibaba.dubbo.config.annotation.Service注解来声明要暴露的服务。

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
  port: 9090
spring:
  application:
    name: spingboot-dubbo-provider

dubbo:
  application:
    id: spring-dubbo-provider
    name: spring-dubbo-provider
  registry: # 配置注册中心
    protocol: zookeeper # 指定注册中心协议
    address: localhost:2181 # 指定注册中心地址
  protocol: # 配置协议
    name: dubbo
    port: 20880
#  monitor:
#    protocol: registry  # 监控中心协议，如果为protocol="registry"，表示从注册中心发现监控中心地址，否则直连监控中心。

```

### 创建springboot-dubbo-provider启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
@EnableDubbo	// @EnableDubbo <==> @EnableDubboConfig + @DubboComponentScan
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
```
### 创建服务提供者实现
```
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.tyrone.springboot.dubbo.api.DemoService;

/*
 * 通过com.alibaba.dubbo.config.annotation.Service注解来声明要暴露的接口
 * 
 * @Service(interfaceClass = DemoService.class)相当于配置文件中的<dubbo:service interface="cn.tyrone.springboot.dubbo.api.DemoService" />
 * 
 */
@Service(interfaceClass = DemoService.class)
@Component
public class DemoServiceImpl implements DemoService {
	
	@Override
	public String sayHello(String name) {
		return name + ":SpringBoot Dubbo Integrate.......";
	}
}

```

博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[SpringBoot 2.0 集成 Dubbo](https://blog.csdn.net/myNameIssls/article/details/82669224)

参考链接:  <br>
[https://github.com/apache/incubator-dubbo](https://github.com/apache/incubator-dubbo) <br>
[https://github.com/apache/incubator-dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples](https://github.com/apache/incubator-dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples) <br>











