package com.example.myapp.models;

public class LoginResponseModel {
    //kết quả trả về API login
//    "status": true,
//    "message": "Login successfully!",
//    "user": {},
//    "token": null
    private Boolean status;
    private UserModel user;
    private String token;

    public LoginResponseModel() {
    }

    public LoginResponseModel(Boolean status, UserModel user, String token) {
        this.status = status;
        this.user = user;
        this.token = token;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
