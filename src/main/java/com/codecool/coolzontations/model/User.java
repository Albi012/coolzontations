package com.codecool.coolzontations.model;

public class User {

    private static int counter = 0;

    private String username;
    private int id;
    private Level level;

    public User(String username, Level level) {
        this.username = username;
        this.level = level;
        this.id = counter++;
    }

    public String getUsername() {
        return username;
    }

    public Level getLevel() {
        return level;
    }
}
