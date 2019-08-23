package cn.tyrone.springboot.example.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "config.author")
@PropertySource(value = "classpath:config.properties", encoding = "UTF-8")
public class ConfigProperties {
    private String name;
    private String age;
    private String addr;
}
