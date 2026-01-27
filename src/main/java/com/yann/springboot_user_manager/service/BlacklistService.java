package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.entity.BlacklistedToken;
import com.yann.springboot_user_manager.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BlacklistService {

    private final BlacklistedTokenRepository repository;

    public BlacklistService(BlacklistedTokenRepository repository) {
        this.repository = repository;
    }

    public void blacklist(String token, Instant expiryDate) {
        BlacklistedToken blacklisted = new BlacklistedToken();
        blacklisted.setToken(token);
        blacklisted.setExpiryDate(expiryDate);
        repository.save(blacklisted);
    }

    public boolean isBlacklisted(String token) {
        return repository.existsByToken(token);
    }
}

