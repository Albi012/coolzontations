package com.codecool.coolzontations.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Consultation {

    @Id
    @GeneratedValue
    @Column(name = "consultationID")
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    @Singular
    @ElementCollection
    private Set<Subject> subjects;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "hosted_consultations")
    @JsonManagedReference
    private User host;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name =  "user_consultation_as_participant",
            joinColumns = {@JoinColumn(name = "consultationID")},
            inverseJoinColumns = { @JoinColumn(name = "userID")})
    @JsonManagedReference
    @ToString.Exclude
    private Set<User> participants = new HashSet<>();

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer participantLimit;

    @Column(nullable = false)
    private String description;

    public boolean findUser(int id) {
        for (User participant : this.getParticipants()) {
            if (participant.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean addParticipant(User user) {
        if (this.participants == null) {
            this.participants = new HashSet<>();
        }
        if (user.getConsultationAsParticipant() == null) {
            user.setConsultationAsParticipant(new HashSet<>());
        }
        this.participants.add(user);
        user.getConsultationAsParticipant().add(this);
        return true;
    }

    public void removeParticipant(User user) {
        this.participants.remove(user);
    }

}
