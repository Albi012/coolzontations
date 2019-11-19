package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ConsultationDataFromRequest {

    private LocalDateTime date;
    private Set<Subject> subjects;
    private User host;
    private Integer duration;
    private Integer participantLimit;
    private String description;

}
