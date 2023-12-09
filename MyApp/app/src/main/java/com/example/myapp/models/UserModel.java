package com.example.myapp.models;

import java.io.Serializable;

public class UserModel implements Serializable {
//    "id": 2,
//    "email": "admin@gmail.com",
//    "password": "23456",
//    "name": "Nguyen Van B",
//    "phone": "0123456789",
//    "address": "Ha Noi",
//    "role": "admin"
    private Integer id;
    private String email, password, name, phone, address, role;

    public UserModel() {
    }

    public UserModel(Integer id, String email, String password, String name, String phone, String address, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
