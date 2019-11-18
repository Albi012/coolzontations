package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.repository.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ConsultationStorage consultationStorage;

    @Autowired
    private DataManager dataManager;

    @PostConstruct
    private void dummyInit() {
        User user1 = User.builder()
                .username("Jancsi")
                .level(Level.ADVANCE)
                .build();
        User user2 = User.builder()
                .username("Gyula")
                .level(Level.PROGBASICS)
                .build();
        User user3 = User.builder()
                .username("Karcsi")
                .level(Level.WEB)
                .build();
        User user4 = User.builder()
                .username("myUser")
                .level(Level.WEB)
                .build();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
        userStorage.addUser(user4);

        Set<Subject> subjects = new HashSet<>(Arrays.asList(Subject.JAVA, Subject.JAVASCRIPT, Subject.REACT));
        Set<User> participants = new HashSet<>(Arrays.asList(user2, user3));
        Set<User> participants2 = new HashSet<>(Arrays.asList(user2, user1));
        Consultation c1 = Consultation.builder()
                .date("Ma")
                .duration(30)
                .host(user1)
                .subjects(subjects)
                .participants(participants)
                .participantLimit(3)
                .description("apacuka fundaluka")
                .build();
        Consultation c2 = Consultation.builder()
                .date("Holnap")
                .duration(90)
                .subjects(subjects)
                .host(user2)
                .participants(participants)
                .participantLimit(1)
                .description("fundakave")
                .build();
        Consultation c3 = Consultation.builder()
                .date("Kedd")
                .duration(120)
                .subjects(subjects)
                .host(user3)
                .participants(participants2)
                .participantLimit(2)
                .description("ap cuk fundaluk funda kave kamanduk")
                .build();

        consultationStorage.addConsultation(c1);
        consultationStorage.addConsultation(c2);
        consultationStorage.addConsultation(c3);

    }

}
