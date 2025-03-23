package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneAndStatus(String phone, Integer status);

    User findByIdAndStatus(Long userId, Integer status);
}
