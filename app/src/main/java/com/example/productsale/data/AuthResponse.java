package com.example.productsale.data;


public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
    private int userId;
    private String role;
    public AuthResponse(String token, String message, int userId,String role,String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.message = message;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
