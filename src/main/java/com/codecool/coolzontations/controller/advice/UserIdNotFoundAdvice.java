package com.codecool.coolzontations.controller.advice;

import com.codecool.coolzontations.service.exceptions.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class UserIdNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userIdNotFoundHandler(UserIdNotFoundException exception){
        return exception.getMessage();
    }
}
