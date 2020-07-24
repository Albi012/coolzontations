package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.UsernameIsTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UsernameIsTakenAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameIsTakenException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String UsernameIsTakenHandler(UsernameIsTakenException ex){
        return ex.getMessage();
    }
}
