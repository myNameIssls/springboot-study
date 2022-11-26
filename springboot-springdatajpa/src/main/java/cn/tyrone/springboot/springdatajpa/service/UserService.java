package cn.tyrone.springboot.springdatajpa.service;

import cn.tyrone.springboot.springdatajpa.entity.UserEntity;
import cn.tyrone.springboot.springdatajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }

}
