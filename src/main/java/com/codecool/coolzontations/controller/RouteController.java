package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.service.ConsultationDataService;
import com.codecool.coolzontations.service.UserDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RouteController {

   @Autowired
   private ConsultationDataService consultationDataService;

   @Autowired
   private ConsultationModelAssembler consultationModelAssembler;

   @Autowired
   private UserDataService userDataService;

    @PostMapping("/registration")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public UserModel userRegistration(@RequestBody RegistrationUserModel registrationUserModel){
        return userDataService.userReg(registrationUserModel);
    }

    @GetMapping("/consultations")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public CollectionModel<EntityModel<EntityModel<Consultation>>> allConsultations(){
        List<EntityModel<Consultation>> allConsultation = consultationDataService.findAllConsultation().stream()
                .map(consultationModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.wrap(allConsultation);
    }

    @GetMapping("/subjects")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<Subject> subjects() {
        return Arrays.asList(Subject.values());
    }

    @PostMapping("/consultation")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Consultation createNewConsultation(
            @RequestBody ConsultationDataFromRequest consultationDataFromRequest){
       return consultationDataService.createNewConsultation(consultationDataFromRequest);
    }

    @GetMapping("/consultation/{id}")
   @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<Consultation> singleConsultation(
            @PathVariable("id") Long id){
        Consultation consultation = consultationDataService.getConsultationById(id);
        return consultationModelAssembler.toModel(consultation);
    }

    @PutMapping("/join-consultation")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Consultation addParticipantToConsultation(@RequestBody DataFromRequest dataFromRequest) {
        return consultationDataService.joinConsultation(dataFromRequest);
    }

    @PutMapping("/cancel-consultation/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void cancelConsultation(
            @ApiParam(value = "ID of the consultation to be cancelled.", required = true)
            @PathVariable("id") Long id){
        consultationDataService.cancelConsultation(id);
    }

    @DeleteMapping("/consultation")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Consultation removeParticipantFromConsultation(@RequestBody DataFromRequest dataFromRequest ) {
        return consultationDataService.removeParticipantFromConsultation(dataFromRequest);
    }

    @GetMapping("/consultations-as-participant/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<Consultation> consultationsAsParticipant(@PathVariable("id") Long id){
        return consultationDataService.getConsultationsAsParticipant(id);
    }

    @GetMapping("/consultations-as-host/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<Consultation> consultationsAsHost(@PathVariable("id") Long id){
        return consultationDataService.getConsultationsAsHost(id);
    }


}
