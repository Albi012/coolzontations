package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.PublicModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationDataService {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation joinConsultation(DataFromRequest dataFromRequest) {
        Long userID = dataFromRequest.getUserID();
        Long consultationID = dataFromRequest.getConsultationID();
        UserModel userModel = userModelRepository.findById(userID).orElseThrow(() -> new UserIdNotFoundException(userID));
        Consultation consultation = consultationRepository.findById(consultationID).orElseThrow(() -> new ConsultationIdNotFoundException(consultationID));
        if (consultation.getParticipantLimit() > consultation.getParticipants().size()) {
            consultation.addParticipant(userModel);
            return consultationRepository.saveAndFlush(consultation);
        }
        throw new ConsultationLimitException(consultation);
    }

    public Consultation removeParticipantFromConsultation(DataFromRequest dataFromRequest) {
        Long userID = dataFromRequest.getUserID();
        UserModel userModel = userModelRepository.findById(userID).orElseThrow(() -> new UserIdNotFoundException(userID));
        Long consultationID = dataFromRequest.getConsultationID();
        Consultation consultation = consultationRepository.findById(consultationID).orElseThrow(() -> new ConsultationIdNotFoundException(consultationID));
        if (consultation.getParticipants().contains(userModel)) {
            consultation.removeParticipant(userModel);
            return consultationRepository.saveAndFlush(consultation);

        }
        throw new UserNotParticipantException(userModel, consultation);
    }

    public Consultation createNewConsultation(ConsultationDataFromRequest consultationDataFromRequest) {
        Long userID = consultationDataFromRequest.getHostID();
        UserModel host = userModelRepository.findById(userID).orElseThrow(() -> new UserIdNotFoundException(userID));
        try {
            Consultation consultation = Consultation.builder()
                    .date(consultationDataFromRequest.getDate())
                    .subjects(consultationDataFromRequest.getAllSubjects())
                    .host(host)
                    .duration(consultationDataFromRequest.getDuration())
                    .participantLimit(consultationDataFromRequest.getParticipantLimit())
                    .description(consultationDataFromRequest.getDescription())
                    .build();
            return consultationRepository.saveAndFlush(consultation);
        } catch (Exception e){
            throw new ConsultationNotCreatedException();
        }
    }

    public void cancelConsultation(Long id) {
        Consultation consultation = consultationRepository.findById(id).orElseThrow(() -> new ConsultationIdNotFoundException(id));
        consultationRepository.delete(consultation);
    }

    public List<Consultation> getConsultationsAsParticipant(Long id) {
        UserModel user = userModelRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
        return consultationRepository.findAll()
                .stream()
                .filter(consultation -> consultation.getParticipants().contains(user))
                .collect(Collectors.toList());
    }

    public List<Consultation> getConsultationsAsHost(Long id) {
        UserModel user = userModelRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
        return consultationRepository.findAll()
                .stream()
                .filter(consultation -> consultation.getHost().equals(user))
                .collect(Collectors.toList());
    }

    public List<Consultation> findArchivedConsultation() {
        LocalDateTime date = LocalDateTime.now();
        return consultationRepository.findArchivedConsultations(date);
    }

    public List<Consultation> findAllConsultation() {
        LocalDateTime date = LocalDateTime.now();
        return consultationRepository.findActiveConsultations(date);
    }


}
