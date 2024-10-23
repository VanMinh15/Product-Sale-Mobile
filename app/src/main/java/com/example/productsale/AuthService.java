package com.example.productsale;

import com.example.productsale.data.ApiResponse;
import com.example.productsale.data.AuthResponse;
import com.example.productsale.data.ForgotPasswordRequest;
import com.example.productsale.data.ForgotPasswordResponse;
import com.example.productsale.data.LoginRequest;
import com.example.productsale.data.RegisterRequest;
import com.example.productsale.data.ResetPasswordRequest;
import com.example.productsale.data.RegisterResponse;
import com.example.productsale.data.ResetPasswordResponse;
import com.example.productsale.data.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface AuthService {

    @POST("/api/v1/auth/authenticate")
    Call<ApiResponse<AuthResponse>> authenticate(@Body LoginRequest loginRequest);

    @POST("/api/v1/auth/register")
    Call<ApiResponse<RegisterResponse>> register(@Body RegisterRequest registerRequest);

    @POST("/api/v1/auth/forgot-password")
    Call<ApiResponse<ForgotPasswordResponse>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("/api/v1/auth/reset-password")
    Call<ApiResponse<ResetPasswordResponse>> resetPassword(
            @Query("token") String token,
            @Body ResetPasswordRequest resetPasswordRequest
    );
    @GET("/api/v1/auth/verify-email")
    Call<ApiResponse<VerifyEmailResponse>> verifyEmail(@Query("token") String token);

}
