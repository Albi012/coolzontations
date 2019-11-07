package com.codecool.coolzontations.model;

import org.springframework.stereotype.Component;

@Component
public class DataFromRequest {

    private Integer userID;
    private Integer consultationID;

    public DataFromRequest() {
    }

    public DataFromRequest(Integer userID, Integer consultationID) {
        this.userID = userID;
        this.consultationID = consultationID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Integer consultationID) {
        this.consultationID = consultationID;
    }
}
