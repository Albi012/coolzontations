package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserRepository;
import com.codecool.coolzontations.service.DataManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class RouteController {

   @Autowired
   private DataManger dataManger;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private DataManger dataManger;

   @Autowired
   private ConsultationRepository consultationRepository;

    @GetMapping("/consultations")
    public List<Consultation> consultations(){
        return consultationRepository.findAll();
    }

    @GetMapping("/users")
    public List<User> users(){
        return userRepository.findAll();
    }


    @PostMapping("/joinConsultation")
    public boolean addParticipantToConsultation(@RequestBody DataFromRequest dataFromRequest) {
        return dataManger.joinConsultation(dataFromRequest);
    }

    @PostMapping("/dropConsultation")
    public boolean removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        return dataManger.removeParticipantFromConsultation(dataFromRequest);
    }

    @PostMapping("/createNewConsultation")
    public void createNewConsultation(@RequestBody ConsultationDataFromRequest consultationDataFromRequest){
        dataManger.createNewConsultation(consultationDataFromRequest);
    }

    @GetMapping("/myJoinedConsultations/{id}")
    public List<Consultation> myConsultations(@PathVariable("id") Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> consultationRepository.findAll().stream().filter(consultation -> consultation.getParticipants().contains(value)).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @GetMapping("/myHostedConsultations/{id}")
    public List<Consultation> myHostedConsultations(@PathVariable("id") Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> consultationRepository.findAll().stream().filter(consultation -> consultation.getHost().equals(user.get())).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }


    @PostMapping("/registration")
    public boolean userRegistration(@RequestBody UserModel userModel){
        return dataManger.userReg(userModel);
    }

}
