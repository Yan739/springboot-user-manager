package com.yann.springboot_user_manager.controller;

import com.yann.springboot_user_manager.dto.AuthResponseDTO;
import com.yann.springboot_user_manager.dto.LoginDTO;
import com.yann.springboot_user_manager.dto.RefreshTokenDTO;
import com.yann.springboot_user_manager.dto.RegisterDTO;
import com.yann.springboot_user_manager.exception.InvalidJwtException;
import com.yann.springboot_user_manager.service.AuthService;
import com.yann.springboot_user_manager.service.BlacklistService;
import com.yann.springboot_user_manager.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final BlacklistService blacklistService;

    public AuthController(AuthService authService, JwtService jwtService, BlacklistService blacklistService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.blacklistService = blacklistService;
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

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidJwtException("Authorization header missing or malformed");
        }

        String token = authHeader.substring(7);

        try {
            Instant expiry = jwtService.extractExpiration(token).toInstant();
            blacklistService.blacklist(token, expiry);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid JWT token");
        }
    }

}
