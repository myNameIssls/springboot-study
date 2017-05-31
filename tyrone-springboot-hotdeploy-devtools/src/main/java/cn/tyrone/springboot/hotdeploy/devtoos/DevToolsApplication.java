package cn.tyrone.springboot.hotdeploy.devtoos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class DevToolsApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DevToolsApplication.class, args);
	}
	
	@RequestMapping("/")
    @ResponseBody
    String home(){
        return "Hello World!!!你真好啊！！！！是真的吗？";
    }
	
	
}
