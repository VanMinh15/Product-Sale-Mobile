package com.example.productsale.data;

public class ForgotPasswordResponse {
    private String message;

    // Constructor
    public ForgotPasswordResponse(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
