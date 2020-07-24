package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.PublicModel;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.service.exceptions.ConsultationNotFoundException;
import com.codecool.coolzontations.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataManger {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ModelWrapper modelWrapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ResponseEntity joinConsultation(DataFromRequest dataFromRequest) {
        Long userID = dataFromRequest.getUserID();
        UserModel userModel = userModelRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));
        Long consultationID = dataFromRequest.getConsultationID();
        Consultation consultation = consultationRepository.findById(consultationID).orElseThrow(() -> new ConsultationNotFoundException(consultationID));
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
            consultation.addParticipant(userModel);
            consultationRepository.saveAndFlush(consultation);
            return ResponseEntity.ok("Successful join.");
        }
        return ResponseEntity.unprocessableEntity().body("Consultation is full.");
    }

    public ResponseEntity removeParticipantFromConsultation(DataFromRequest dataFromRequest) {
        Long userID = dataFromRequest.getUserID();
        UserModel userModel = userModelRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));
        Long consultationID = dataFromRequest.getConsultationID();
        Consultation consultation = consultationRepository.findById(consultationID).orElseThrow(() -> new ConsultationNotFoundException(consultationID));
        if (consultation.getParticipants().contains(userModel)) {
            consultation.removeParticipant(userModel);
            consultationRepository.saveAndFlush(consultation);
            return ResponseEntity.ok("Successful delete.");
        }
        return ResponseEntity.unprocessableEntity().body("Delete process was aborted.");
    }

    public ResponseEntity createNewConsultation(ConsultationDataFromRequest consultationDataFromRequest) {
        Long userID = consultationDataFromRequest.getHostID();
        UserModel host = userModelRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));
        try {
            Consultation consultation = Consultation.builder()
                    .date(consultationDataFromRequest.getDate())
                    .subjects(consultationDataFromRequest.getAllSubjects())
                    .host(host)
                    .duration(consultationDataFromRequest.getDuration())
                    .participantLimit(consultationDataFromRequest.getParticipantLimit())
                    .description(consultationDataFromRequest.getDescription())
                    .build();
            consultationRepository.saveAndFlush(consultation);
            return ResponseEntity.ok(consultation);
        } catch (Exception e){
            return ResponseEntity.unprocessableEntity().body("Cannot create consultation.");
        }
    }

    public ResponseEntity userReg(RegistrationUserModel registrationUserModel) {
        if (!userModelRepository.existsByUsername(registrationUserModel.getUsername())) {
            if (!userModelRepository.existsByEmail(registrationUserModel.getEmail())) {
                UserModel userModel = UserModel.builder()
                        .level(registrationUserModel.getLevel())
                        .username(registrationUserModel.getUsername())
                        .email(registrationUserModel.getEmail())
                        .password(passwordEncoder().encode(registrationUserModel.getPassword1()))
                        .build();
                userModelRepository.save(userModel);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.unprocessableEntity().body("Email is already registered");
            }
        } else {
            return ResponseEntity.unprocessableEntity().body("Username already taken.");
        }
    }

    public ResponseEntity cancelConsultation(Long id) {
        try{
            Consultation consultation = consultationRepository.findById(id).orElseThrow(() -> new ConsultationNotFoundException(id));
            consultationRepository.delete(consultation);
            return ResponseEntity.ok().body("Successful");
        } catch(Exception e){
            return ResponseEntity.unprocessableEntity().body("No consultation for this id: " + id);
        }

    }

    public Optional<UserModel> findUserByUsername(String username){
        return userModelRepository.findByUsername(username);
    }

    public ResponseEntity<List<Consultation>> findAllConsultation() {
        LocalDateTime date = LocalDateTime.now();
        List<Consultation> consultations = consultationRepository.findActiveConsultations(date);
        mapConsultationsUsersToPublicUsers(consultations);
        return ResponseEntity.ok().body(consultations);
    }

    private void mapConsultationsUsersToPublicUsers(List<Consultation> consultations) {
        for (Consultation consultation : consultations) {
            ArrayList<PublicModel> publicModels = new ArrayList<>();
            consultation.setPublicHost(modelWrapper.userModelToParticipantMapper(consultation.getHost()));
            for (UserModel participant : consultation.getParticipants()) {
                publicModels.add(modelWrapper.userModelToParticipantMapper(participant));
            }
            consultation.setPublicParticipants(publicModels);
        }
    }

    public List<UserModel> findAllUser() {
        return userModelRepository.findAll();
    }

    public List<Consultation> getConsultationsAsParticipant(Long id) {
        UserModel user = userModelRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return consultationRepository.findAll()
                .stream()
                .filter(consultation -> consultation.getParticipants().contains(user))
                .collect(Collectors.toList());
    }

    public List<Consultation> getConsultationsAsHost(Long id) {
        UserModel user = userModelRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return consultationRepository.findAll()
                .stream()
                .filter(consultation -> consultation.getHost().equals(user))
                .collect(Collectors.toList());
    }

    public List<Consultation> findArchivedConsultation() {
        LocalDateTime date = LocalDateTime.now();
        return consultationRepository.findArchivedConsultations(date);
    }

}
