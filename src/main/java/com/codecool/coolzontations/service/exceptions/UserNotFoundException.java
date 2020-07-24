package com.codecool.coolzontations.service.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userID) {
        super("User could not find: " + userID);
    }
}
