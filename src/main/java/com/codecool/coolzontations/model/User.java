package com.codecool.coolzontations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @NotEmpty
    private String username;
    @NotEmpty
    private int id;
    private Level level;


}
