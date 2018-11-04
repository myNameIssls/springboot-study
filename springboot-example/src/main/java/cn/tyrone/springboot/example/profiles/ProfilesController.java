package cn.tyrone.springboot.example.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfilesController {

    @Autowired
    private Environment env;

    @RequestMapping("/index")
    public String index() {
        String spring_profiles_active = env.getProperty("spring.profiles.active");
        String server_port = env.getProperty("server.port");
        return "spring.profiles.active: " + spring_profiles_active + "server.port: " + server_port;
    }

}
