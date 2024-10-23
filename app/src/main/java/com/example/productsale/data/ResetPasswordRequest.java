package com.example.productsale.data;

import androidx.constraintlayout.widget.Guideline;

public class ResetPasswordRequest {
    private String Token;
    private String password;
    private String confirmPassword;
    public  ResetPasswordRequest(String Token,String password,String confirmPassword){
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
