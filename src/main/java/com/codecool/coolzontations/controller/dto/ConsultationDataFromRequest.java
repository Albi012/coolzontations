package com.codecool.coolzontations.controller.dto;

import com.codecool.coolzontations.model.Subject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class ConsultationDataFromRequest {

    @ApiModelProperty(notes = "Start date of the consultation")
    private LocalDateTime date;
    private Long hostID;
    private Integer duration;
    private Integer participantLimit;
    private String description;
    private List<String> subjects;


    public Set<Subject> getAllSubjects() {
        Set<Subject> result = new HashSet<>();
        for (String subjectString: this.subjects) {
            result.add(Subject.getSubjectByName(subjectString));
        }
        return result;
    }

}
