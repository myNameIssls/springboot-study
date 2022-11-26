package cn.tyrone.springboot.springdatajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
// 扫描被@Entity注解的实体
@EntityScan(basePackages = {"cn.tyrone.springboot.springdatajpa.entity"})
// 启动jpaRepository
@EnableJpaRepositories(basePackages = {"cn.tyrone.springboot.springdatajpa.repository"})
@SpringBootApplication
public class SpringDataJpaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("------- 启动成功 ----------");
    }
}
