package com.codecool.coolzontations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserModel {


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

    @Singular
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "ROLES", joinColumns = @JoinColumn( name = "userID"))
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Roles> roles;

    public void addConsultation(Consultation c){
        if(this.hostedConsultations == null){
            this.hostedConsultations = new HashSet<>();
        }
        c.setHost(this);
        this.hostedConsultations.add(c);
    }

    public List<String> getRolesAsString(){
        return this.roles.stream().map(Roles::toString).collect(Collectors.toList());
    }


    public void removeHostedConsultatuin(Consultation consultation){
        this.hostedConsultations.remove(consultation);
    }
}
