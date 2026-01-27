package com.yann.springboot_user_manager.controller;

import com.yann.springboot_user_manager.dto.LoginDTO;
import com.yann.springboot_user_manager.dto.RegisterDTO;
import com.yann.springboot_user_manager.dto.UserDTO;
import com.yann.springboot_user_manager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        String token = authService.loginAndGetToken(dto);
        return ResponseEntity.ok(token);
    }
}
