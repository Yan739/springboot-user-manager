package com.yann.springboot_user_manager.mapper;

import com.yann.springboot_user_manager.dto.UserCreateDTO;
import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.entity.User;


public class UserMapper {

    public UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public User toEntity(UserCreateDTO dto){
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }
}
