package com.example.productsale.ui.authenticate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.productsale.HomeActivity;
import com.example.productsale.R;
import com.example.productsale.data.ApiResponse;
import com.example.productsale.data.LoginRequest;
import com.example.productsale.data.AuthResponse;
import com.example.productsale.AuthService;
import com.example.productsale.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Retrofit service
        authService = ApiClient.getClient().create(AuthService.class);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sign Up Button Click
        TextView tvSignUp = findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Forget Password Button Click
        TextView tvForgetPassword = findViewById(R.id.tv_forget_password);
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Forget Password screen
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Login Button Click
        TextView tvHome = findViewById(R.id.btn_login);
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsername = findViewById(R.id.et_username);
                EditText etPassword = findViewById(R.id.et_password);
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                LoginRequest request = new LoginRequest(username, password);

                Call<ApiResponse<AuthResponse>> call = authService.authenticate(request);
                call.enqueue(new Callback<ApiResponse<AuthResponse>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<AuthResponse>> call, Response<ApiResponse<AuthResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<AuthResponse> loginResponse = response.body();

                            if (loginResponse.getStatus() == 200) {
                                // Extract data from response
                                int userId = loginResponse.getData().getUserId();
                                String accessToken = loginResponse.getData().getAccessToken();
                                String refreshToken = loginResponse.getData().getRefreshToken();
                                String role = loginResponse.getData().getRole();

                                // Handle successful login
                                Log.d("LoginSuccess", "User ID: " + userId);
                                Log.d("LoginSuccess", "Access Token: " + accessToken);
                                Log.d("LoginSuccess", "Refresh Token: " + refreshToken);
                                Log.d("LoginSuccess", "Role: " + role);

                                // Save tokens
                                saveTokens(accessToken, refreshToken);

                                // Navigate to another activity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle case where status is not 200
                                Log.e("LoginError", "Login failed with status: " + loginResponse.getStatus());
                                Toast.makeText(LoginActivity.this, "Login failed with status: " + loginResponse.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(LoginActivity.this, "Unsuccessful login" , Toast.LENGTH_SHORT).show();
                            Log.e("LoginError", "Unsuccessful login: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<AuthResponse>> call, Throwable t) {
                        // Handle network failure
                        Log.e("NetworkError", t.getMessage());
                    }
                });
            }

            private void saveTokens(String accessToken, String refreshToken) {
                SharedPreferences sharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("AccessToken", accessToken);
                editor.putString("RefreshToken", refreshToken);
                editor.apply();
            }
        });
    }
}
