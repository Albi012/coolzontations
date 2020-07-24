package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.service.exceptions.EmailAlreadyRegisteredException;
import com.codecool.coolzontations.service.exceptions.UserNameNotFoundException;
import com.codecool.coolzontations.service.exceptions.UsernameIsTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    @Autowired
    private UserModelRepository userModelRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserModel userReg(RegistrationUserModel registrationUserModel) {
        String username = registrationUserModel.getUsername();
        if (!userModelRepository.existsByUsername(username)) {
            String email = registrationUserModel.getEmail();
            if (!userModelRepository.existsByEmail(email)) {
                UserModel userModel = UserModel.builder()
                        .level(registrationUserModel.getLevel())
                        .username(registrationUserModel.getUsername())
                        .email(registrationUserModel.getEmail())
                        .password(passwordEncoder().encode(registrationUserModel.getPassword1()))
                        .build();
                return userModelRepository.save(userModel);
            } else {
               throw new EmailAlreadyRegisteredException(email);
            }
        } else {
            throw new UsernameIsTakenException(username);
        }
    }

    public UserModel findUserByUsername(String username){
        return userModelRepository.findByUsername(username).orElseThrow(() -> new UserNameNotFoundException(username));
    }


    public List<UserModel> findAllUser() {
        return userModelRepository.findAll();
    }

}
