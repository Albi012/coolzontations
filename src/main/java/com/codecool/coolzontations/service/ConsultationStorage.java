package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.Consultation;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;

@Service
public class ConsultationStorage {


    private List<Consultation> consultations = new LinkedList<>();

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    @Transient
    public List<Consultation> getConsultations() {
        return consultations;
    }

    public Consultation getConsultationByID(Integer id){
        Optional<Consultation> foundConsultation = consultations.stream().filter(user -> user.getId()==id).findFirst();
        return foundConsultation.orElseThrow();

    }
}


