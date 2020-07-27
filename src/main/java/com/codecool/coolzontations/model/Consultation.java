package com.codecool.coolzontations.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private UserModel host;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name =  "user_consultation_as_participant",
            joinColumns = {@JoinColumn(name = "consultationID")},
            inverseJoinColumns = { @JoinColumn(name = "userID")})
    @ToString.Exclude
    private Set<UserModel> participants = new HashSet<>();

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer participantLimit;

    @Column(nullable = false)
    private String description;

    public boolean findUser(int id) {
        for (UserModel participant : this.getParticipants()) {
            if (participant.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addParticipant(UserModel userModel) {
        if (this.participants == null) {
            this.participants = new HashSet<>();
        }
        if (userModel.getConsultationAsParticipant() == null) {
            userModel.setConsultationAsParticipant(new HashSet<>());
        }
        this.participants.add(userModel);
        userModel.getConsultationAsParticipant().add(this);
    }

    public void removeParticipant(UserModel userModel) {
        this.participants.remove(userModel);
    }


}
