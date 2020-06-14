package com.example.wonderfullindonesia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.model.Status;
import com.example.wonderfullindonesia.rest.ApiService;
import com.example.wonderfullindonesia.rest.ApiUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtUsername;
    TextInputEditText edtPassword;
    Button btnLogin, btnRegister;
    ApiService apiService;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();
        pref = getApplicationContext().getSharedPreferences("Users", 0); // 0 - for private mode
        boolean statusLogin = pref.getBoolean("statusLogin", false);
        if (statusLogin){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        constraintLayout = findViewById(R.id.constraintLayout);
        progressBar = findViewById(R.id.progressBar);
        apiService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Objects.requireNonNull(edtUsername.getText()).toString();
                String password = Objects.requireNonNull(edtPassword.getText()).toString();
                if(validateLogin(username, password)){
                    doLogin(username, password);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void doLogin(final String username,final String password) {
        Call<Status> call = apiService.login(username, password);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call call,@NonNull Response response) {
                if (response.isSuccessful()) {
                    Status resObj = (Status) response.body();
                    assert resObj != null;
                    if (resObj.getStatus().equals("success")) {
                        editor = pref.edit();
                        editor.putBoolean("statusLogin", true);
                        Log.e("RAG", "onResponse: " + resObj.getId() );
                        editor.putInt("idUsers", resObj.getId());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    } else {
                        Snackbar.make(constraintLayout, "The username or password is incorrect", Snackbar.LENGTH_SHORT)
                                .show();
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                } else {
                    Snackbar.make(constraintLayout, "Error! Please try again!", Snackbar.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                Log.e("RAG", "onFailure: " + t.getMessage());
                Snackbar.make(constraintLayout, "Error! Please try again!", Snackbar.LENGTH_SHORT)
                        .show();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Snackbar.make(constraintLayout, "Username is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Snackbar.make(constraintLayout, "Password is required", Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}