package com.example.myapp.models;

public class ForgotPWResponseModel {
    private String message;
    private Boolean status;

    public ForgotPWResponseModel(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public ForgotPWResponseModel() {
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
