package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.DataFromRequest;
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
        DataFromRequest dataFromRequest = objectMapper.readValue(string, DataFromRequest.class);
        dataManager.joinConsultation(dataFromRequest.getUserID(), dataFromRequest.getConsultationID());
    }

    @PostMapping("/dropConsultation")
    public void removeParticipantFromConsultation(@RequestBody String string ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataFromRequest dataFromRequest = objectMapper.readValue(string, DataFromRequest.class);
        dataManager.dropConsultation(dataFromRequest.getUserID(), dataFromRequest.getConsultationID());
    }

    @GetMapping("/myConsultations/{id}")
    public List<Consultation> myConsultations(@PathVariable("id") Integer id){
        return dataManager.getMyConsultations(id);

    }


}