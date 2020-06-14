package com.example.wonderfullindonesia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etFullName, etContact, etAddress, etUsername, etPassword;
    Button btnRegister;
    ApiService apiService;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();
        pref = getApplicationContext().getSharedPreferences("Users", 0); // 0 - for private mode
        boolean statusLogin = pref.getBoolean("statusLogin", false);
        if (statusLogin){
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etFullName = findViewById(R.id.etFull_name);
        etContact = findViewById(R.id.etContact);
        etAddress = findViewById(R.id.etAddress);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        constraintLayout = findViewById(R.id.constraintLayout);
        apiService = ApiUtils.getUserService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = Objects.requireNonNull(etFullName.getText()).toString();
                String contact = Objects.requireNonNull(etContact.getText()).toString();
                String address = Objects.requireNonNull(etAddress.getText()).toString();
                String username = Objects.requireNonNull(etUsername.getText()).toString();
                String password = Objects.requireNonNull(etPassword.getText()).toString();
                //validate form
                if(validateLogin(full_name, contact, address, username, password)){
                    //do login
                    doLogin(full_name, contact, address, username, password);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void doLogin(String full_name, String contact, String address, String username, String password) {
        Call<Status> call = apiService.register(full_name, contact, address, username, password);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                if (response.isSuccessful()) {
                    Status resObj = (Status) response.body();
                    assert resObj != null;
                    if (resObj.getStatus().equals("success")) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (resObj.getStatus().equals("error")){
                        Snackbar.make(constraintLayout, "The username already taken", Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                    Snackbar.make(constraintLayout, "Error! Please try again!", Snackbar.LENGTH_SHORT)
                            .show();
                    }
                } else {
                    Snackbar.make(constraintLayout, "Error! Please try again!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                Snackbar.make(constraintLayout, Objects.requireNonNull(t.getMessage()), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private boolean validateLogin(String full_name,String contact, String address, String username, String password){
        if(full_name == null || full_name.trim().length() == 0){
            Snackbar.make(constraintLayout, "Full Name is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(contact == null || contact.trim().length() == 0){
            Snackbar.make(constraintLayout, "Contact is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(address == null || address.trim().length() == 0){
            Snackbar.make(constraintLayout, "Address is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(username == null || username.trim().length() == 0){
            Snackbar.make(constraintLayout, "Username is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Snackbar.make(constraintLayout, "Password is required", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
    
}