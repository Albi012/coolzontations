package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.ConsultationLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConsultationLimitAdvice {

    @ResponseBody
    @ExceptionHandler(ConsultationLimitException.class)
    @ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
    String ConsultationIsFullHandler(ConsultationLimitException ex){
        return ex.getMessage();
    }
}
