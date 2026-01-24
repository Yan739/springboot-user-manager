package com.yann.springboot_user_manager.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Utilisateur avec l'id " + id + " introuvable");
    }
}
