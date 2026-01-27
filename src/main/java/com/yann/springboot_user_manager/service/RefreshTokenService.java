package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.entity.RefreshToken;
import com.yann.springboot_user_manager.entity.User;
import com.yann.springboot_user_manager.repository.RefreshTokenRepository;
import com.yann.springboot_user_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public RefreshToken create(User user) {

        repository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        return repository.save(token);
    }

    public RefreshToken verify(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token invalide"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expiré");
        }

        return refreshToken;
    }
}
