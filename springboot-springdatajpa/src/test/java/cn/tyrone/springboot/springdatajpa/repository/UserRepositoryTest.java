package cn.tyrone.springboot.springdatajpa.repository;

import cn.tyrone.springboot.springdatajpa.SpringDataJpaApplication;
import cn.tyrone.springboot.springdatajpa.entity.Gender;
import cn.tyrone.springboot.springdatajpa.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringDataJpaApplication.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        UserEntity userEntity = UserEntity.builder()
                .userCode(RandomStringUtils.randomNumeric(5))
                .name("Jack Ma")
                .password("123456")
                .gender(Gender.OTHER)
                .build();
        this.userRepository.save(userEntity);
    }

}