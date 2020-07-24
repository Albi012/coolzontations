package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.ConsultationNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConsultationNotCreatedAdvice {

    @ResponseBody
    @ExceptionHandler(ConsultationNotCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConsultationNotCreatedHandler(ConsultationNotCreatedException ex){
        return ex.getMessage();
    }
}
