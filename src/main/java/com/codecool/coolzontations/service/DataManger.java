package com.codecool.coolzontations.service;

import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataManger {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean userReg(UserModel userModel) {

        if(!userRepository.findByUsername(userModel.getUsername()))
        User user = User.builder()
                .level(userModel.getLevel())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .password(passwordEncoder().encode(userModel.getPassword()))
                .build();
        userRepository.save(user);
        return true;


    }
}
