package com.codecool.coolzontations.service.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userID) {
        super("User does not exists with this id: " + userID);
    }
}
