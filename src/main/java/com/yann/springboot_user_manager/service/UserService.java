package com.yann.springboot_user_manager.service;

import com.yann.springboot_user_manager.dto.UserCreateDTO;
import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.entity.User;
import com.yann.springboot_user_manager.exception.UserNotFoundException;
import com.yann.springboot_user_manager.mapper.UserMapper;
import com.yann.springboot_user_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO save(UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
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
}
