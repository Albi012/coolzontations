package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
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
        if(!userModelRepository.existsByUsername(registrationUserModel.getUsername())){
            if(!userModelRepository.existsByEmail(registrationUserModel.getEmail())){
                UserModel userModel = UserModel.builder()
                        .level(registrationUserModel.getLevel())
                        .username(registrationUserModel.getUsername())
                        .email(registrationUserModel.getEmail())
                        .password(passwordEncoder().encode(registrationUserModel.getPassword()))
                        .build();
                userModelRepository.save(userModel);
                return "Registration successful";
            } else {
                return "Email already in use";
            }
        } else {
            return "Username already in use";
        }

    }

    public String cancelConsultation(Long id) {
        Consultation consultation = consultationRepository.findById(id).orElseThrow();
        consultationRepository.delete(consultation);
        return "Consultation was successfully deleted from the system!";
    }
}
