package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.service.ConsultationStorage;
import com.codecool.coolzontations.service.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataManager {

    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ConsultationStorage consultationStorage;

    public List<Consultation> getConsultations() {
        return consultationStorage.getConsultations();
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public void joinConsultation(Integer userID, Integer consultationID) {
        User user = userStorage.getUserByID(userID);
        Consultation consultation = consultationStorage.getConsultationByID(consultationID);
        consultation.addParticipant(user);
    }

    public void dropConsultation(Integer userID, Integer consultationID) {
        User user = userStorage.getUserByID(userID);
        Consultation consultation = consultationStorage.getConsultationByID(consultationID);
        consultation.removeParticipant(user);
    }

    public List<Consultation> getMyConsultations(int userID) {
        User user = userStorage.getUserByID(userID);
        List<Consultation> myConsultations = new ArrayList<>();
        for (Consultation consultation : consultationStorage.getConsultations()) {
            if(consultation.getParticipants().contains(user)){
                myConsultations.add(consultation);
            }
        }
        return myConsultations;
    }

}