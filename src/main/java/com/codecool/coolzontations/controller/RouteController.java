package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserRepository;
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
   private UserRepository userRepository;

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
        User user = userRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
            if(consultation.getParticipantLimit()> consultation.getParticipants().size()) {
                consultation.addParticipant(user);
                consultationRepository.saveAndFlush(consultation);
    return true;
            }
        return false;
    }

    @PostMapping("/dropConsultation")
    public boolean removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        User user = userRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
            if(consultation.getParticipantLimit()> consultation.getParticipants().size()) {
                consultation.removeParticipant(user);
                consultationRepository.saveAndFlush(consultation);
                return true;
            }
        return false;
    }

    @PostMapping("/createNewConsultation")
    public void createNewConsultation(@RequestBody ConsultationDataFromRequest c){
        User host = userRepository.findById(c.getHostID()).orElseThrow();
        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now()) // TODO
                .subject(Subject.JAVA) // TODO
                .host(host)
                .duration(c.getDuration())
                .participantLimit(c.getParticipantLimit())
                .description(c.getDescription())
                .build();
    consultationRepository.saveAndFlush(consultation);
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

}
