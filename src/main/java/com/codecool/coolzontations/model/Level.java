package com.codecool.coolzontations.model;

public enum Level {

    PROGBASICS(1),
    WEB(2),
    OOP(3),
    ADVANCE(4);

    private int level;

    Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
