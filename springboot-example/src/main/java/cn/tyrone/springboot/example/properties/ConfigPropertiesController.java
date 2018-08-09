package cn.tyrone.springboot.example.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertiesController {

	@Autowired private ConfigProperties configProperties;
	
	@RequestMapping("/config")
	public String config() {
		
		String config = "config.author.name: " + configProperties.getName()
						+ ", config.author.age:" + configProperties.getAge() 
						+ ", config.author.addr:" + configProperties.getAddr();
		
		return config;
	}
}
