package com.codecool.coolzontations.controller.dto.response;

import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.UserModel;
import lombok.*;
import org.springframework.context.annotation.Bean;


@Data
public class ResponseUserModel {

    private Long id;

    private String username;

    private Level level;

    private String email;

}
