package com.codecool.coolzontations.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RegistrationUserModel {

    private String username;
    private Level level;
    private String email;
    private String password1;
    private String password2;

}