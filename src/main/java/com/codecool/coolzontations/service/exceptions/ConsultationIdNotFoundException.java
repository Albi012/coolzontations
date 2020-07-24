package com.codecool.coolzontations.service.exceptions;

public class ConsultationIdNotFoundException extends RuntimeException {
    public ConsultationIdNotFoundException(Long consultationID) {
        super("Consultation could not find: " + consultationID);
    }
}
