package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalAdvice {

    @ResponseBody
    @ExceptionHandler(ConsultationNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConsultationIdNotFoundHandler(ConsultationNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConsultationNotCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConsultationNotCreatedHandler(ConsultationNotCreatedException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConsultationLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ConsultationIsFullHandler(ConsultationLimitException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String EmailIsTakenHandler(EmailAlreadyRegisteredException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userIdNotFoundHandler(UserNotFoundException exception){
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UsernameIsTakenException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String UsernameIsTakenHandler(UsernameIsTakenException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNameNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userNameNotFoundHandler(UserNameNotFoundException exception){
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotParticipantException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UserNotParticipantHandler(UserNotParticipantException ex){
        return ex.getMessage();
    }
}
