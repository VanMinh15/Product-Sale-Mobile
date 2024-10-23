package com.example.productsale.ui.authenticate;

import android.content.Intent;
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

import com.example.productsale.ApiClient;
import com.example.productsale.AuthService;
import com.example.productsale.R;
import com.example.productsale.data.ApiResponse;
import com.example.productsale.data.AuthResponse;
import com.example.productsale.data.RegisterRequest;
import com.example.productsale.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = ApiClient.getClient().create(AuthService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_sign_up), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện khi nhấn vào dòng chữ Sign In
        TextView tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình LoginActivity
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView tvSignUp = findViewById(R.id.btn_sign_up);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsername = findViewById(R.id.et_username);
                EditText etPassword = findViewById(R.id.et_password);
                EditText etFullName = findViewById(R.id.et_fullname);
                EditText etPhone = findViewById(R.id.et_phone);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String fullName = etFullName.getText().toString();
                String phone = etPhone.getText().toString();

                RegisterRequest request = new RegisterRequest(fullName,username,password,phone);
                Call<ApiResponse<RegisterResponse>> call = authService.register(request);
                call.enqueue(new Callback<ApiResponse<RegisterResponse>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<RegisterResponse>> call, Response<ApiResponse<RegisterResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<RegisterResponse> registerResponse = response.body();

                            if (registerResponse.getStatus() == 200) {
                                // Extract data from response
                                // Handle successful login
                                // Navigate to another activity
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle case where status is not 200
                                Log.e("LoginError", "Login failed with status: " + registerResponse.getStatus());
                                Toast.makeText(SignUpActivity.this, "Login failed with status: " + registerResponse.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(SignUpActivity.this, "Unsuccessful Register: ", Toast.LENGTH_SHORT).show();
                            Log.e("Register Error", "Unsuccessful Register: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<RegisterResponse>> call, Throwable t) {
                        // Handle network failure
                        Log.e("NetworkError", t.getMessage());
                    }
                });

            }
        });
    }
}
