package com.codecool.coolzontations.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Consultation {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    @Singular
    @ElementCollection
    private Set<Subject> subjects;

    @ManyToOne
    private User host;

    @Singular
    @ManyToMany(mappedBy = "consultationAsParticipant", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<User> participants;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer participantLimit;

    @Column(nullable = false)
    private String description;

    public Consultation(LocalDate date, int duration, Set<Subject> subjects, User host, Set<User> participants, int participantLimit, String description) {
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

//    public String getDate() {
//        return date;
//    }

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

    public boolean findUser(int id) {
        for (User participant : this.getParticipants()) {
            if (participant.getId() == id){
                return true;
            }
        }
        return false;
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
