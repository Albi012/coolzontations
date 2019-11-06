package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.repository.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RouteController {

   @Autowired
    private DataManager dataManager;

    @GetMapping("/consultations")
    public List<Consultation> consultations(){
        return dataManager.getConsultations();
    }

    @GetMapping("/users")
    public List<User> users(){
        return dataManager.getUsers();
    }

    @PostMapping("/joinConsultation")
    public void addParticipantToConsultation(@RequestBody User user, @RequestBody Consultation consultation) {
        dataManager.joinConsultation(user, consultation);
    }


}
