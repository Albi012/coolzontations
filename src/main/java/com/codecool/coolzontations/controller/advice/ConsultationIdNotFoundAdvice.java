package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.ConsultationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConsultationIdNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ConsultationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ConsultationIdNotFoundHandler(ConsultationNotFoundException ex){
        return ex.getMessage();
    }
}
