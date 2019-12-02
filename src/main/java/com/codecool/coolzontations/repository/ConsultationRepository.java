package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.ConsultationDataFromRequest;
import com.codecool.coolzontations.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
