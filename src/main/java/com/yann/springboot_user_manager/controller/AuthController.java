package com.yann.springboot_user_manager.controller;

import com.yann.springboot_user_manager.dto.AuthResponseDTO;
import com.yann.springboot_user_manager.dto.LoginDTO;
import com.yann.springboot_user_manager.dto.RefreshTokenDTO;
import com.yann.springboot_user_manager.dto.RegisterDTO;
import com.yann.springboot_user_manager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            @Valid @RequestBody RefreshTokenDTO dto) {

        String newAccessToken = authService.refresh(dto);
        return ResponseEntity.ok(newAccessToken);
    }
}
