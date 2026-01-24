package com.yann.springboot_user_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yann.springboot_user_manager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
