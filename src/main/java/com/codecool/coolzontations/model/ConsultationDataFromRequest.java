package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ConsultationDataFromRequest {

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Long hostID;
    private Integer duration;
    private Integer participantLimit;
    private String description;
    private List<String> subjects;

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.getYear(),this.getMonth(),this.getDay(),this.getHour(),this.getMinute());
    }

    public Set<Subject> getAllSubjects() {
        Set<Subject> result = new HashSet<>();
        for (String subjectString: this.subjects) {
            result.add(Subject.getSubjectByName(subjectString));
        }
        return result;
    }

}
