package cn.tyrone.springboot.springdatajpa.service;

import cn.tyrone.springboot.springdatajpa.SpringDataJpaApplication;
import cn.tyrone.springboot.springdatajpa.entity.Gender;
import cn.tyrone.springboot.springdatajpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringDataJpaApplication.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void saveUser() {
        UserEntity userEntity = UserEntity.builder()
                .userCode("000001")
                .name("Jack Ma")
                .password("123456")
                .gender(Gender.MAN)
                .build();
        userService.saveUser(userEntity);
    }
}