package com.example.productsale.data;

public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String phone;

    public RegisterRequest(String fullName,String username, String password,String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }
}