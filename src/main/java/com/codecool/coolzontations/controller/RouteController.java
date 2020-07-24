package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.service.ConsultationDataService;
import com.codecool.coolzontations.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class RouteController {

   @Autowired
   private ConsultationDataService consultationDataService;


   @Autowired
   private UserDataService userDataService;

    @PostMapping("/registration")
    public UserModel userRegistration(@RequestBody RegistrationUserModel registrationUserModel){
        return userDataService.userReg(registrationUserModel);
    }

    @GetMapping("/consultations")
    public List<Consultation> consultations(){
        return consultationDataService.findAllConsultation();
    }

    @GetMapping("/subjects")
    public List<Subject> subjects() {
        return Arrays.asList(Subject.values());
    }

    @PostMapping("/consultation")
    public Consultation createNewConsultation(@RequestBody ConsultationDataFromRequest consultationDataFromRequest){
       return consultationDataService.createNewConsultation(consultationDataFromRequest);
    }

    @PutMapping("/join-consultation")
    public Consultation addParticipantToConsultation(@RequestBody DataFromRequest dataFromRequest) {
        return consultationDataService.joinConsultation(dataFromRequest);
    }

    @PutMapping("/cancel-consultation/{id}")
    public void cancelConsultation(@PathVariable("id") Long id){
        consultationDataService.cancelConsultation(id);
    }

    @DeleteMapping("/consultation")
    public Consultation removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        return consultationDataService.removeParticipantFromConsultation(dataFromRequest);
    }

    @GetMapping("/consultations-as-participant/{id}")
    public List<Consultation> consultationsAsParticipant(@PathVariable("id") Long id){
        return consultationDataService.getConsultationsAsParticipant(id);
    }

    @GetMapping("/consultations-as-host/{id}")
    public List<Consultation> consultationsAsHost(@PathVariable("id") Long id){
        return consultationDataService.getConsultationsAsHost(id);
    }


}
