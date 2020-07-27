package com.codecool.coolzontations.service.util;

import com.codecool.coolzontations.controller.dto.response.ResponseConsultationModel;
import com.codecool.coolzontations.controller.dto.response.ResponseUserModel;
import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Util {


    public ResponseUserModel setUserProperties(UserModel usermodel){
        ResponseUserModel model = new ResponseUserModel();
        model.setId(usermodel.getId());
        model.setUsername(usermodel.getUsername());
        model.setLevel(usermodel.getLevel());
        model.setEmail(usermodel.getEmail());
        return model;
    }


    public ResponseConsultationModel setConsultationProperties(Consultation consultation){
        ResponseConsultationModel consultationModel = new ResponseConsultationModel();
        consultationModel.setId(consultation.getId());
        consultationModel.setDate(consultation.getDate());
        consultationModel.setSubjects(consultation.getSubjects());
        consultationModel.setHost(setUserProperties(consultation.getHost()));
        consultationModel.setParticipants(userModelWrapper(consultation.getParticipants()));
        consultationModel.setDuration(consultation.getDuration());
        consultationModel.setParticipantLimit(consultation.getParticipantLimit());
        consultationModel.setDescription(consultation.getDescription());
        return consultationModel;
    }

    private Set<ResponseUserModel> userModelWrapper(Set<UserModel> userModels){
        return userModels.stream()
                .map(this::setUserProperties)
                .collect(Collectors.toSet());
    }
}
