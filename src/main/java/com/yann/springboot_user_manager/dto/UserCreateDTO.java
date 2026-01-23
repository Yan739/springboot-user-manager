package com.yann.springboot_user_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotBlank
    String name;

    @Email
    String email;
}
