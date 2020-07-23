package com.codecool.coolzontations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codecool.coolzontations.model.Consultation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("SELECT c FROM Consultation c WHERE c.date > :date ORDER BY c.date ")
    List<Consultation> findActiveConsultations(@Param("date") LocalDateTime date);

    @Query("SELECT c FROM Consultation c WHERE c.date < :date ORDER BY c.date ")
    List<Consultation> findArchivedConsultations(@Param("date") LocalDateTime date);



}
