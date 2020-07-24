package com.codecool.coolzontations.service.exceptions;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String username) {
        super("Username is already registered: " + username);
    }
}
