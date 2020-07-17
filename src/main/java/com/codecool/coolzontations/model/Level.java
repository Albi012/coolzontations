package com.codecool.coolzontations.model;

public enum Level {

    PROGBASICS(1L),
    WEB(2L),
    OOP(3L),
    ADVANCE(4L);

    private Long level;

    Level(Long level) {
        this.level = level;
    }

    public Long getLevel() {
        return level;
    }
}
