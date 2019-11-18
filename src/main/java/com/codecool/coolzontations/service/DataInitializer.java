package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.repository.DataManager;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//@Component
public class DataInitializer {

    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ConsultationStorage consultationStorage;

    @Autowired
    private DataManager dataManager;

//    @PostConstruct
    private void dummyInit() {
        User user1 = new User("Jancsi", Level.ADVANCE);
        User user2 = new User("Gyula", Level.PROGBASICS);
        User user3 = new User("Karcsi", Level.WEB);
        User user4 = new User("myUser", Level.WEB);
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
        userStorage.addUser(user4);

        Set<Subject> subjects = new HashSet<>(Arrays.asList(Subject.JAVA, Subject.JAVASCRIPT, Subject.REACT));
        Set<User> participants = new HashSet<>(Arrays.asList(user2, user3));
        Set<User> participants2 = new HashSet<>(Arrays.asList(user2, user1));
        Consultation c1 = new Consultation(LocalDate.now(), 30, subjects, user1, participants, 3, "apacuka fundaluka");
        Consultation c2 = new Consultation(LocalDate.now(), 90, subjects, user2, participants, 1, "fundakave kamanduka");
        Consultation c3 = new Consultation(LocalDate.now(), 120, subjects, user3, participants2, 2, "ap cuk fundaluk funda kave kamanduk");

        consultationStorage.addConsultation(c1);
        consultationStorage.addConsultation(c2);
        consultationStorage.addConsultation(c3);

    }

}
