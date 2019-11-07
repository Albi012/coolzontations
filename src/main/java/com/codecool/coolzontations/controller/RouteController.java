package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.JoinData;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.repository.DataManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void addParticipantToConsultation(@RequestBody String string ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JoinData joinData = objectMapper.readValue(string, JoinData.class);
        dataManager.joinConsultation(joinData.getUserID(), joinData.getConsultationID());
    }

    @GetMapping("/myConsultations/{id}")
    public List<Consultation> myConsultations(@PathVariable("id") Integer id){
        System.out.println("id: " + id);
        return dataManager.getMyConsultations(id);

    }


}
