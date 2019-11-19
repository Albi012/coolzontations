package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    private int id;
    private String date;
    private Set<Subject> subjects;
    private User host;
    private Set<User> participants;
    private int duration;
    private int participantLimit;
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

    public void removeParticipant(User user) {
        this.participants.remove(user);
    }

}
