package com.yann.springboot_user_manager.mapper;

import com.yann.springboot_user_manager.dto.RegisterDTO;
import com.yann.springboot_user_manager.dto.UserCreateDTO;
import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.entity.User;

public final class UserMapper {

    private UserMapper() {

    }

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    public static User toEntity(RegisterDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }
}
