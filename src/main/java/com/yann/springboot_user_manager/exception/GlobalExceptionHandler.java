package com.yann.springboot_user_manager.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class GlobalExceptionHandler {

    @NotBlank
    String nom;

    @Email
    String email;
}
