package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.dto.RegisterDTO;
import com.yann.springboot_user_manager.entity.User;
import com.yann.springboot_user_manager.mapper.UserMapper;
import com.yann.springboot_user_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register (RegisterDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }
}
