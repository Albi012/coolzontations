package com.codecool.coolzontations.controller.dto;


import com.codecool.coolzontations.model.Level;
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
