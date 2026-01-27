package com.yann.springboot_user_manager.controller;

import com.yann.springboot_user_manager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TestSecurityController {

    public TestSecurityController() {
    }

    @GetMapping("/secured")
    public String secured() {
        return "ACCESS OK";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "ADMIN OK";
    }
}
