package cn.tyrone.springboot.dubbo.provider.service.impl;

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
