package com.codecool.coolzontations.model;

import javax.validation.constraints.NotEmpty;

public class User {

    private static int counter = 0;

    @NotEmpty
    private String username;
    @NotEmpty
    private int id;
    private Level level;

    public User(String username, Level level) {
        this.username = username;
        this.level = level;
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level.getLevel();
    }
}
