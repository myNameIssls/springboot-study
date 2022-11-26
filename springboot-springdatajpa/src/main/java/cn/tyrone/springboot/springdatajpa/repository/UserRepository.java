package cn.tyrone.springboot.springdatajpa.repository;

import cn.tyrone.springboot.springdatajpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
