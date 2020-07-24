package com.codecool.coolzontations.service.exceptions;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(Long userID) {
        super("User could not find: " + userID);
    }
}
