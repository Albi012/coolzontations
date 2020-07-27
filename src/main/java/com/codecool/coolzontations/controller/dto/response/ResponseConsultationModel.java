package com.codecool.coolzontations.controller.dto.response;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.UserModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ResponseConsultationModel {


    private Long id;
    private LocalDateTime date;
    private Set<Subject> subjects;
    private ResponseUserModel host;
    private Set<ResponseUserModel> participants = new HashSet<>();
    private Integer duration;
    private Integer participantLimit;
    private String description;



}
