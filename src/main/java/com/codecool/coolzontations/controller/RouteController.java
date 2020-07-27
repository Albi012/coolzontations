package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.controller.dto.response.ResponseConsultationModel;
import com.codecool.coolzontations.controller.dto.response.ResponseUserModel;
import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.model.assembler.ConsultationModelAssembler;
import com.codecool.coolzontations.model.assembler.UserModelAssembler;
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
   private UserModelAssembler userModelAssembler;

   @Autowired
   private UserDataService userDataService;

    @PostMapping(value = "/registration")
    @ApiOperation(
            value = "Handle user registration",
            notes = "Save the user to DB if the provided information is valid",
            response = ResponseUserModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseUserModel> userRegistration(
            @ApiParam(name = "Required user details to fulfill registration", required = true)
            @RequestBody RegistrationUserModel registrationUserModel){
        return userModelAssembler.toModel(userDataService.userReg(registrationUserModel));
    }

    @GetMapping("/consultations")
    @ApiOperation(
            value = "Find all the upcoming consultations",
            notes = "Returns all available consultations for the users.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public CollectionModel<EntityModel<ResponseConsultationModel>> allConsultations(){
        List<Consultation> activeConsultation = consultationDataService.findActiveConsultation();
        return consultationModelAssembler.toCollectionModel(activeConsultation);
    }

    @GetMapping("/subjects")
    @ApiOperation(
            value = "Find all the consultation subjects.",
            notes = "Returns all available subjects for consultations.",
            response = Subject.class,
            authorizations = { @Authorization(value="jwtToken") })
    public List<Subject> subjects() {
        return Arrays.asList(Subject.values());
    }

    @PostMapping("/consultation")
    @ApiOperation(
            value = "Create a new consultation",
            notes = "The service tries to create and save a new consultation in the DB with the provided information",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseConsultationModel> createNewConsultation(
            @ApiParam(value = "All the specific information for the consultation", required = true)
            @RequestBody ConsultationDataFromRequest consultationDataFromRequest){
       return consultationModelAssembler.toModel(consultationDataService.createNewConsultation(consultationDataFromRequest));
    }

    @GetMapping("/consultation/{id}")
    @ApiOperation(
            value = "Find a selected consultation",
            notes = "Returns a consultation with the provided ID if it`s exist.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseConsultationModel> singleConsultation(
            @ApiParam(value = "ID of the consultation to be returned.", required = true)
            @PathVariable("id") Long id){
        Consultation consultation = consultationDataService.getConsultationById(id);
        return consultationModelAssembler.toModel(consultation);
    }

    @PutMapping("/join-consultation")
    @ApiOperation(
            value = "Join a specific consultation.",
            notes = "A specific user can join to a specific consultation.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseConsultationModel> addParticipantToConsultation(
            @ApiParam(value = "IDs of the consultation and the user", required = true)
            @RequestBody DataFromRequest dataFromRequest) {
        return consultationModelAssembler.toModel(consultationDataService.joinConsultation(dataFromRequest));
    }

    @DeleteMapping("/cancel-consultation/{id}")
    @ApiOperation(
            value = "Delete consultation",
            notes = "Remove the consultation from the database with the provided ID.",
            authorizations = { @Authorization(value="jwtToken") })
    public void cancelConsultation(
            @ApiParam(value = "ID of the consultation to be cancelled.", required = true)
            @PathVariable("id") Long id){
        consultationDataService.cancelConsultation(id);
    }

    @PutMapping("/consultation-participants")
    @ApiOperation(
            value = "Remove a praticipant from consultation",
            notes = "With the provided IDs, a user will be removed from a consultation.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseConsultationModel> removeParticipantFromConsultation(
            @ApiParam(value = "IDs of the consultation and the user", required = true)
            @RequestBody DataFromRequest dataFromRequest ) {
        return consultationModelAssembler.toModel(consultationDataService.removeParticipantFromConsultation(dataFromRequest));
    }

    @GetMapping("/consultations-as-participant/{id}")
    @ApiOperation(
            value = "A specific user`s consultations as participant",
            notes = "Returns all the consultation for a user with the provided ID.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public CollectionModel<EntityModel<ResponseConsultationModel>> consultationsAsParticipant(
            @ApiParam(value = "ID of the user", required = true)
            @PathVariable("id") Long id){
        return consultationModelAssembler.toCollectionModel(consultationDataService.getConsultationsAsParticipant(id));
    }

    @GetMapping("/consultations-as-host/{id}")
    @ApiOperation(
            value = "A specific user`s consultations as host",
            notes = "Returns all the consultation for a user with the provided ID.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public CollectionModel<EntityModel<ResponseConsultationModel>> consultationsAsHost(
            @ApiParam(value = "ID of the user", required = true)
            @PathVariable("id") Long id){
        return consultationModelAssembler.toCollectionModel(consultationDataService.getConsultationsAsHost(id));
    }

    @GetMapping("/user/{username}")
    @ApiOperation(
            value = "A specific user`s public details",
            notes = "Returns some public information about the user.",
            response = ResponseConsultationModel.class,
            authorizations = { @Authorization(value="jwtToken") })
    public EntityModel<ResponseUserModel> findUserByName(
            @ApiParam(value = "Name of the user", required = true)
            @PathVariable("username") String username){
        return userModelAssembler.toModel(userDataService.findUserByUsername(username));
    }




}
