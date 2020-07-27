package com.codecool.coolzontations.service.exceptions;

public class ConsultationNotFoundException extends RuntimeException {
    public ConsultationNotFoundException(Long consultationID) {
        super("Consultation could not find with this ID: " + consultationID);
    }
}
