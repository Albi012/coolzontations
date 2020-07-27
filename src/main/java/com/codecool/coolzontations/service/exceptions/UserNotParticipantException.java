package com.codecool.coolzontations.service.exceptions;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.UserModel;


public class UserNotParticipantException extends RuntimeException {
    public UserNotParticipantException(UserModel userModel, Consultation consultation) {
        super("Participant does not applied to consultation! \n User: \n" + userModel.getUsername() + " \n Consultation: \n" + consultation);
    }
}
