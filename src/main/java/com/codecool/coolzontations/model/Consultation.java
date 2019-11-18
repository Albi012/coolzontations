package com.codecool.coolzontations.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

}
