package com.codecool.coolzontations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codecool.coolzontations.model.Consultation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("select c from Consultation c join fetch c.participants p where p.id=:userId")
    List<Consultation> findAllByParticipantsContaining(@Param("userId") Long userID);



}
