package com.yann.springboot_user_manager.repository;

import com.yann.springboot_user_manager.entity.RefreshToken;
import com.yann.springboot_user_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}

