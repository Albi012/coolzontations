package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.UserCredentials;
import com.codecool.coolzontations.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String,Object>> signin(@RequestBody UserCredentials data) {
        return authenticationService.authenticationValiadtion(data);
    }


}
