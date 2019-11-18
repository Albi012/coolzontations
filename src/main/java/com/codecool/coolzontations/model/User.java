package com.codecool.coolzontations.model;

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
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToMany(mappedBy = "host")
    @EqualsAndHashCode.Exclude
    private Set<Consultation> hostedConsultations;

    @ManyToMany
    private Set<Consultation> consultationAsParticipant;


    public void addConsultation(Consultation c){
        if(this.hostedConsultations == null){
            this.hostedConsultations = new HashSet<>();
        }
        c.setHost(this);
        this.hostedConsultations.add(c);
    }


}
