package com.example.productsale.data;

public class VerifyEmailResponse {
    private String message;

    // Constructor
    public VerifyEmailResponse(String message) {
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
