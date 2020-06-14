package com.example.wonderfullindonesia.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.model.Users;
import com.example.wonderfullindonesia.rest.ApiService;
import com.example.wonderfullindonesia.rest.ApiUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    TextInputEditText etFullName, etContact, etAddress, etUsername, etPassword;
    ApiService apiService;
    int idCode;
    SharedPreferences pref;
    Button btnUpdate, btnLogOut, btnDelete;
    SharedPreferences.Editor editor;
    String password = "0";

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).setTitle("Profile");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        apiService = ApiUtils.getUserService();
        etFullName = view.findViewById(R.id.etFull_name);
        etContact = view.findViewById(R.id.etContact);
        etAddress = view.findViewById(R.id.etAddress);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnDelete = view.findViewById(R.id.btnDelete);

        pref = this.getContext().getSharedPreferences("Users", 0); // 0 - for private mode
        idCode = pref.getInt("idUsers", 0);

        Call<List<com.example.wonderfullindonesia.model.Response>> call = apiService.getProfile(idCode);
        call.enqueue(new Callback<List<com.example.wonderfullindonesia.model.Response>>() {
            @Override
            public void onResponse(Call<List<com.example.wonderfullindonesia.model.Response>> call, Response<List<com.example.wonderfullindonesia.model.Response>> response) {
                Log.d("RAG", "onResponse: " + response);
                try{
                    generateDataList(response.body());
                }catch(Exception e){
                    //Log.e("",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<com.example.wonderfullindonesia.model.Response>> call, Throwable t) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Users>> call = apiService.updateProfile(idCode, etFullName.getText().toString(), etContact.getText().toString(),
                        etAddress.getText().toString(), etUsername.getText().toString(), password);
                call.enqueue(new Callback<List<Users>>() {
                    @Override
                    public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Update Berhasil", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Users>> call, Throwable t) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Users>> call = apiService.deleteProfile(idCode);
                call.enqueue(new Callback<List<Users>>() {
                    @Override
                    public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(Call<List<Users>> call, Throwable t) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        getActivity().finish();
                    }
                });
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

    private void generateDataList(List<com.example.wonderfullindonesia.model.Response> body) {
        etFullName.setText(body.get(0).getFull_name());
        etContact.setText(body.get(0).getContact());
        etAddress.setText(body.get(0).getAddress());
        etUsername.setText(body.get(0).getUsername());
        password = body.get(0).getPassword();

    }
}