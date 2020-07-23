package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.UserCredentials;
import com.codecool.coolzontations.security.LoginAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginAuth loginAuth;

    @PostMapping("/sign-in")
    public ResponseEntity signin(@RequestBody UserCredentials data) {
        return loginAuth.authenticationValiadtion(data);
    }


}
