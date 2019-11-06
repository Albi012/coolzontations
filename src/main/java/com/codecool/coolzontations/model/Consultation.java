package com.codecool.coolzontations.model;

import java.util.Set;

public class Consultation {

    private static int counter;

    private int id;
    private String date;
    private int duration;
    private Set<Subject> subjects;
    private User host;
    private Set<User> participants;
    private int participantLimit;
    private String description;

    public Consultation(String date, int duration, Set<Subject> subjects, User host, Set<User> participants, int participantLimit, String description) {
        this.id = counter++;
        this.date = date;
        this.duration = duration;
        this.subjects = subjects;
        this.host = host;
        this.participants = participants;
        this.participantLimit = participantLimit;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public User getHost() {
        return host;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public int getParticipantLimit() {
        return participantLimit;
    }

    public String getDescription() {
        return description;
    }

    public boolean addParticipant(User user) {
        if (participants.size()<participantLimit) {
            this.participants.add(user);
            return true;
        }
        //TODO: add exception
        return false;
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
