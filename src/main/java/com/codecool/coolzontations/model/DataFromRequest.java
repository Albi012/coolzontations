package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DataFromRequest {

    private Long userID;
    private Long consultationID;
}
