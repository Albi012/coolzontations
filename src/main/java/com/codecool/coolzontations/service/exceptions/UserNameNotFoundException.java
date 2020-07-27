package com.codecool.coolzontations.service.exceptions;

public class UserNameNotFoundException extends RuntimeException {


    public UserNameNotFoundException(String name) {
        super("User could not find, please inspect the provided name.: " + name);
    }
}
