package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.service.ConsultationDataService;
import com.codecool.coolzontations.service.UserDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ConsultationDataService consultationDataService;

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/users")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<UserModel> users(){
        return userDataService.findAllUser();
    }

    @GetMapping("/archived-consultations")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<Consultation> consultations(){
        return consultationDataService.findArchivedConsultation();
    }
}
