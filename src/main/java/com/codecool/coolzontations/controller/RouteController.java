package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        Optional<User> user = userRepository.findById(dataFromRequest.getUserID());
        Optional<Consultation> consultation = consultationRepository.findById(dataFromRequest.getConsultationID());
        if(consultation.isPresent() && user.isPresent()){
            if(consultation.get().getParticipantLimit()> consultation.get().getParticipants().size()) {
                consultation.get().addParticipant(user.get());
                consultationRepository.saveAndFlush(consultation.get());
                return true;
            }
        }
        return false;
    }

    @PostMapping("/dropConsultation")
    public boolean removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        Optional<User> user = userRepository.findById(dataFromRequest.getUserID());
        Optional<Consultation> consultation = consultationRepository.findById(dataFromRequest.getConsultationID());
        if(consultation.isPresent() && user.isPresent()){
            if(consultation.get().getParticipantLimit()> consultation.get().getParticipants().size()) {
                consultation.get().removeParticipant(user.get());
                consultationRepository.saveAndFlush(consultation.get());
                return true;
            }
        }
        return false;
    }

    @PostMapping("/createNewConsultation")
    public void createNewConsultation(@RequestBody ConsultationDataFromRequest c){
        Optional<User> host = userRepository.findById(c.getHostID());
        if (host.isPresent()) {
            Consultation consultation = Consultation.builder()
                .date(c.getDate())
                .subjects(c.getAllSubjects())
                .host(host.get())
                .duration(c.getDuration())
                .participantLimit(c.getParticipantLimit())
                .description(c.getDescription())
                .build();
            consultationRepository.save(consultation);
        }
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

    @GetMapping("/subjects")
    public List<Subject> subjects() {
        return Arrays.asList(Subject.values());
    }
}
