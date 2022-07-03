package com.plats.allinoutservice.accountservice.pojo;

public class ErrorResponse {

    private final String errMessage;

    public ErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
