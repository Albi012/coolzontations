package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataManger {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean joinConsultation(DataFromRequest dataFromRequest) {
        User user = userRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
            consultation.addParticipant(user);
            consultationRepository.saveAndFlush(consultation);
            return true;
        }
        return false;
    }

    public boolean removeParticipantFromConsultation(DataFromRequest dataFromRequest) {
        User user = userRepository.findById(dataFromRequest.getUserID()).orElseThrow();
        Consultation consultation = consultationRepository.findById(dataFromRequest.getConsultationID()).orElseThrow();
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
            consultation.removeParticipant(user);
            consultationRepository.saveAndFlush(consultation);
            return true;
        }
        return false;
    }

    public void createNewConsultation(ConsultationDataFromRequest consultationDataFromRequest) {
        User host = userRepository.findById(consultationDataFromRequest.getHostID()).orElseThrow();
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

    public String userReg(UserModel userModel) {
        if(!userRepository.findByUsername(userModel.getUsername())){
            if(!userRepository.findByEmail(userModel.getEmail())){
                User user = User.builder()
                        .level(userModel.getLevel())
                        .username(userModel.getUsername())
                        .email(userModel.getEmail())
                        .password(passwordEncoder().encode(userModel.getPassword()))
                        .build();
                userRepository.save(user);
                return "Registration successful";
            } else {
                return "Email already in use";
            }
        } else {
            return "Username already in use";
        }

    }
}
