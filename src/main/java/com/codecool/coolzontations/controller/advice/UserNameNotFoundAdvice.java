package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.UserNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNameNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNameNotFoundHandler(UserNameNotFoundException exception){
        return exception.getMessage();
    }
}
