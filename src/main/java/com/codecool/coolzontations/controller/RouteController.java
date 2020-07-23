package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.service.DataManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class RouteController {

   @Autowired
   private DataManger dataManger;


    @PostMapping("/registration")
    public ResponseEntity userRegistration(@RequestBody RegistrationUserModel registrationUserModel){
        return dataManger.userReg(registrationUserModel);
    }

    @GetMapping("/consultations")
    public ResponseEntity<List<Consultation>> consultations(){
        return dataManger.findAllConsultation();
    }

    @GetMapping("/subjects")
    public List<Subject> subjects() {
        return Arrays.asList(Subject.values());
    }

    @PostMapping("/consultation")
    public void createNewConsultation(@RequestBody ConsultationDataFromRequest consultationDataFromRequest){
        dataManger.createNewConsultation(consultationDataFromRequest);
    }

    @PutMapping("/join-consultation")
    public boolean addParticipantToConsultation(@RequestBody DataFromRequest dataFromRequest) {
        return dataManger.joinConsultation(dataFromRequest);
    }

    @PutMapping("/cancel-consultation/{id}")
    public String cancelConsultation(@PathVariable("id") Long id){
        return dataManger.cancelConsultation(id);
    }

    @DeleteMapping("/consultation")
    public boolean removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        return dataManger.removeParticipantFromConsultation(dataFromRequest);
    }

    @GetMapping("/consultations-as-participant/{id}")
    public List<Consultation> consultationsAsParticipant(@PathVariable("id") Long id){
        return dataManger.getConsultationsAsParticipant(id);
    }

    @GetMapping("/consultations-as-host/{id}")
    public List<Consultation> consultationsAsHost(@PathVariable("id") Long id){
        return dataManger.getConsultationsAsHost(id);
    }


}
