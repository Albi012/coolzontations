package com.codecool.coolzontations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {


    @Id
    @GeneratedValue
    @Column(name = "userID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToMany(mappedBy = "host")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Set<Consultation> hostedConsultations;

    @ManyToMany(mappedBy = "participants")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ToString.Exclude
    private Set<Consultation> consultationAsParticipant;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public void addConsultation(Consultation c){
        if(this.hostedConsultations == null){
            this.hostedConsultations = new HashSet<>();
        }
        c.setHost(this);
        this.hostedConsultations.add(c);
    }


}
