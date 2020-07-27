package com.codecool.coolzontations.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {


    @GetMapping("/error")
    public Map<String, Object> handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, Object> response = new HashMap<>();
        response.put("Status Code", status.toString());
        return response;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
