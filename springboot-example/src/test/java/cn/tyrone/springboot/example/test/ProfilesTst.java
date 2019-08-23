package cn.tyrone.springboot.example.test;

import cn.tyrone.springboot.example.SpringBootExampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类用于测试SpringBoot多环境
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootExampleApplication.class)
public class ProfilesTst {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;
    @Autowired
    HttpServletRequest request;

    @Test
    public void test() {
        String spring_profiles_active = env.getProperty("spring.profiles.active");
        String server_port = env.getProperty("server.port");
        int serverPort = request.getServerPort();
        logger.info("------------- spring.profiles.active: " + spring_profiles_active + "\tserver.port: " + serverPort + "--------------");
    }
}
