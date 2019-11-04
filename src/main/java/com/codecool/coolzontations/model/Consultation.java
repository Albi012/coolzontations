package com.codecool.coolzontations.model;

import java.util.Set;

public class Consultation {

    private String date;
    private int duration;
    private Set<Subject> subjects;
    private User host;
    private Set<User> participants;
    private int participantLimit;
    private String description;

    public Consultation(String date, int duration, Set<Subject> subjects, User host, Set<User> participants, int participantLimit, String description) {
        this.date = date;
        this.duration = duration;
        this.subjects = subjects;
        this.host = host;
        this.participants = participants;
        this.participantLimit = participantLimit;
        this.description = description;
    }


    @Override
    public String toString() {
        return "Consultation{" +
                "date='" + date + '\'' +
                ", duration=" + duration +
                ", subjects=" + subjects +
                ", host=" + host +
                ", participants=" + participants +
                ", participantLimit=" + participantLimit +
                ", description='" + description + '\'' +
                '}';
    }
}
