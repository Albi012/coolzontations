package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataManger {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

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
}
