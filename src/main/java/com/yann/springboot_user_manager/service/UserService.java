package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.dto.UserCreateDTO;
import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.dto.UserUpdateDTO;
import com.yann.springboot_user_manager.entity.User;
import com.yann.springboot_user_manager.exception.UserNotFoundException;
import com.yann.springboot_user_manager.mapper.UserMapper;
import com.yann.springboot_user_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO update(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }


    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toDTO(user);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }


    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        if (!checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Identifiants invalides");
        }

        return UserMapper.toDTO(user);
    }

}
