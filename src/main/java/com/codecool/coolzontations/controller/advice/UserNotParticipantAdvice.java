package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.UserNotParticipantException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotParticipantAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotParticipantException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UserNotParticipantHandler(UserNotParticipantException ex){
        return ex.getMessage();
    }
}
