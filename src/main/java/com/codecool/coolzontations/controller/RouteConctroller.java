package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.service.ConsultationStorage;
import com.codecool.coolzontations.service.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteConctroller {

    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ConsultationStorage consultationStorage;

    @GetMapping("/consultations")
    public List<Consultation> consultations(){
        return consultationStorage.getConsultations();

    }

    @GetMapping("/users")
    public List<User> users(){
        return userStorage.getUsers();

    }

}
