package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.PublicModel;
import com.codecool.coolzontations.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public class ModelWrapper {

    public PublicModel userModelToParticipantMapper(UserModel model){
        return PublicModel.builder()
                .id(model.getId())
                .email(model.getEmail())
                .level(model.getLevel())
                .username(model.getUsername())
                .build();
    }
}
