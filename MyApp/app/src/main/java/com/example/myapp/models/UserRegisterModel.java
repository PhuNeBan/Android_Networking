package com.example.myapp.models;

public class UserRegisterModel extends UserModel{
    private String confirmPassword;

    public UserRegisterModel() {
    }

    public String getConfirm_password() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
