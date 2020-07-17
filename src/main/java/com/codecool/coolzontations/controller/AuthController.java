package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.UserCredentials;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.security.JwtTokenService;
import com.codecool.coolzontations.service.DataManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenServices;

    @Autowired
    private DataManger dataManger;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenServices, UserModelRepository users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody UserCredentials data) {
        try {
            String username = data.getUsername();
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            Optional<UserModel> user = dataManger.findUserByUsername(username);

                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("username", user.get().getUsername());
                userDetails.put("level", user.get().getLevel());
                userDetails.put("id", user.get().getId());

                Map<Object, Object> model = new HashMap<>();
                model.put("user", userDetails);
                model.put("token", token);
                return ResponseEntity.ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
