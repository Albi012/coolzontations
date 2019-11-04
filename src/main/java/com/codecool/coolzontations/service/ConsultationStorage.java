package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.User;
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
}
