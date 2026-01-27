package com.yann.springboot_user_manager.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    public String accessToken;
    public String refreshToken;
}
