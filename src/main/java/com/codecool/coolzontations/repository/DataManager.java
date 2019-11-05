package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.service.ConsultationStorage;
import com.codecool.coolzontations.service.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataManager {

    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ConsultationStorage consultationStorage;

    public List<Consultation> getConsultations(){
        return consultationStorage.getConsultations();

    }

    public List<User> getUsers(){
        return userStorage.getUsers();

    }

}
