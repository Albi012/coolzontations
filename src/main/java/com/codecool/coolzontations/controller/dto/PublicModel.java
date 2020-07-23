package com.codecool.coolzontations.controller.dto;

import com.codecool.coolzontations.model.Level;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicModel {

    private Long id;

    private String username;

    private Level level;

    private String email;
}
