package com.codecool.coolzontations.security;

import com.codecool.coolzontations.controller.dto.UserCredentials;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.service.ConsultationDataService;
import com.codecool.coolzontations.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginAuth {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenServices;

    @Autowired
    private UserDataService userDataService;

    public LoginAuth(AuthenticationManager authenticationManager, JwtTokenService jwtTokenServices, UserModelRepository users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    public ResponseEntity authenticationValiadtion(@RequestBody UserCredentials data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = getUserRoles(authentication);
            String token = jwtTokenServices.createToken(username, roles);
            UserModel user = userDataService.findUserByUsername(username);
            Map<String, Object> userDetails = mapUserDetails(user);
            Map<Object, Object> model = authModelBuilder(token, userDetails);

            return ResponseEntity.ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    private List<String> getUserRoles(Authentication authentication) {
        return authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
    }

    private Map<Object, Object> authModelBuilder(String token, Map<String, Object> userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("user", userDetails);
        model.put("token", token);
        return model;
    }

    private Map<String, Object> mapUserDetails(UserModel user) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUsername());
        userDetails.put("level", user.getLevel());
        userDetails.put("role", user.getRoles());
        userDetails.put("id", user.getId());
        return userDetails;
    }
}
