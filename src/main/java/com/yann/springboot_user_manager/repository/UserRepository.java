package com.yann.springboot_user_manager.repository;

import com.yann.springboot_user_manager.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import com.yann.springboot_user_manager.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

