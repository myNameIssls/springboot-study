package cn.tyrone.springboot.integrate.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.tyrone.springboot.integrate.mybatis.dao.UserMapper;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan	
public class Application implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... arg0) throws Exception {

        List<Map<String, Object>> list = userMapper.list();
        System.out.println(list);

        List<Map<String, Object>> listxml = userMapper.listxml();
        System.out.println(listxml);
    }

}
