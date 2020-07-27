package com.codecool.coolzontations.service.exceptions;

public class ConsultationNotCreatedException extends RuntimeException {

    public ConsultationNotCreatedException(){
        super("Consultation could not be created. Please provide ALL the required parameters.");
    }
}
