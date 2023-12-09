package com.example.myapp.models;

public class RegisterResponseModel {
    private Boolean status;
    private String message;
    private UserModel user;

    public RegisterResponseModel() {
    }

    public RegisterResponseModel(Boolean status, String message, UserModel user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
