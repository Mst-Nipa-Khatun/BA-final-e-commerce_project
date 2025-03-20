package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByNameAndStatus(String name,Integer status);
}
