package com.codecool.coolzontations.service.exceptions;

import com.codecool.coolzontations.model.Consultation;

public class ConsultationLimitException extends RuntimeException {

    public ConsultationLimitException(Consultation consultation){
        super("Could not join to consultation" + consultation);
    }
}
