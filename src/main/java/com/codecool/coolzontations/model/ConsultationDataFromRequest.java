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

//    private Set<Subject> subjects;
    private LocalDateTime date;
    private Long hostID;
    private Integer duration;
    private Integer participantLimit;
    private String description;

//    public LocalDateTime getDateTime() {
//        return LocalDateTime.of(this.getYear(),this.getMonth(),this.getDay(),this.getHour(),this.getMinute());
//    }

}
