package com.codecool.coolzontations.service.exceptions;

import com.codecool.coolzontations.model.Consultation;

public class ConsultationLimitException extends RuntimeException {

    public ConsultationLimitException(Consultation consultation){
        super("Consultations is full, could not complete the request." + consultation);
    }
}
