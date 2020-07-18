package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.datamodel.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.datamodel.DataFromRequest;
import com.codecool.coolzontations.controller.datamodel.RegistrationUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean joinConsultation(DataFromRequest dataFromRequest) {
        UserModel userModel = userModelRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
            consultation.addParticipant(userModel);
            consultationRepository.saveAndFlush(consultation);
            return true;
        }
        return false;
    }

    public boolean removeParticipantFromConsultation(DataFromRequest dataFromRequest) {
        UserModel userModel = userModelRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
        if (consultation.getParticipants().contains(userModel)) {
            consultation.removeParticipant(userModel);
            consultationRepository.saveAndFlush(consultation);
            return true;
        }
        return false;
    }

    public void createNewConsultation(ConsultationDataFromRequest consultationDataFromRequest) {
        UserModel host = userModelRepository.findById(consultationDataFromRequest.getHostID()).orElseThrow();
        Consultation consultation = Consultation.builder()
                .date(consultationDataFromRequest.getDate())
                .subjects(consultationDataFromRequest.getAllSubjects())
                .host(host)
                .duration(consultationDataFromRequest.getDuration())
                .participantLimit(consultationDataFromRequest.getParticipantLimit())
                .description(consultationDataFromRequest.getDescription())
                .build();
        consultationRepository.saveAndFlush(consultation);
    }

    public String userReg(RegistrationUserModel registrationUserModel) {
        if (!userModelRepository.existsByUsername(registrationUserModel.getUsername())) {
            if (!userModelRepository.existsByEmail(registrationUserModel.getEmail())) {
                UserModel userModel = UserModel.builder()
                        .level(registrationUserModel.getLevel())
                        .username(registrationUserModel.getUsername())
                        .email(registrationUserModel.getEmail())
                        .password(passwordEncoder().encode(registrationUserModel.getPassword1()))
                        .build();
                userModelRepository.save(userModel);
                return "OK";
            } else {
                return "Email already in use";
            }
        } else {
            return "Username already in use";
        }
    }

    @Transactional
    public String cancelConsultation(Long id) {
        Consultation consultation = consultationRepository.findById(id).orElseThrow();
//        UserModel user = userModelRepository.findById(dataFromRequest.getUserID()).orElseThrow();
//        user.removeHostedConsultatuin(consultation);
//        userModelRepository.saveAndFlush(user);
//        consultation.removeHost();
//        consultation.getParticipants().clear();
//        consultationRepository.saveAndFlush(consultation);
        consultationRepository.delete(consultation);
        return "Consultation was successfully deleted from the system!";

    }

    public Optional<UserModel> findUserByUsername(String username){
        return userModelRepository.findByUsername(username);
    }

    public List<Consultation> findAllConsultation() {
        return consultationRepository.findAll();
    }

    public List<UserModel> findAllUser() {
        return userModelRepository.findAll();
    }

    public List<Consultation> getConsultationsAsParticipant(Long id) {
        Optional<UserModel> user = userModelRepository.findById(id);
        return user.map(value -> consultationRepository.findAll().stream().filter(consultation -> consultation.getParticipants().contains(value)).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    public List<Consultation> getConsultationsAsHost(Long id) {
        Optional<UserModel> user = userModelRepository.findById(id);
        return user.map(value -> consultationRepository.findAll().stream().filter(consultation -> consultation.getHost().equals(user.get())).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
