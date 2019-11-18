package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
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

    @OneToMany(mappedBy = "host", cascade = {CascadeType.PERSIST})
    private Set<Consultation> consultationAsHost;

    @ManyToMany
    private Set<Consultation> consultationAsParticipant;

}
