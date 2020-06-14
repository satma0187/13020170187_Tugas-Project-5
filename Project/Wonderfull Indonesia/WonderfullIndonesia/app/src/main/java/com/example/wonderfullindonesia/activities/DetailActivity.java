package com.example.wonderfullindonesia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.model.Area;
import com.example.wonderfullindonesia.model.Detail;
import com.example.wonderfullindonesia.model.Status;
import com.example.wonderfullindonesia.rest.ApiService;
import com.example.wonderfullindonesia.rest.ApiUtils;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    int idNumber;
    TextView tvHeader;
    ImageView imgHeader;
    ApiService apiService;
    LinearLayout linearLayout;
    List<Detail> movieList;
    int numPosition;
    TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            idNumber = 0;
        } else {
            idNumber= extras.getInt("idChoose");
            numPosition = extras.getInt("code");
        }
        tvDetail = findViewById(R.id.toolbar_title);
        Log.e("RAG", "onCreate: " + idNumber );
        apiService = ApiUtils.getUserService();
        imgHeader = findViewById(R.id.imgHeader);
        tvHeader = findViewById(R.id.tvHeader);
        linearLayout = findViewById(R.id.linearLayout);

        Call<List<Detail>> call = apiService.getDetail(idNumber, numPosition);
        call.enqueue(new Callback<List<Detail>>() {
            @Override
            public void onResponse(@NonNull Call<List<Detail>> call, Response<List<Detail>> response) {
                Log.e("RAG", "onResponse: "+ response.body() );
                try{
                    generateDataList(response.body());
                }catch(Exception e){
                    //Log.e("",e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Detail>> call,@NonNull Throwable t) {
                Log.d("TAG","Response = " + t.getMessage());

            }
        });
    }

    private void generateDataList(List<Detail> body) {
        Log.e("RAG", "generateDataList: " + body.get(0).getDesc() );
        tvHeader.setText(body.get(0).getDesc());
        tvDetail.setText(body.get(0).getTitle());
        Log.e("RAG", "generateDataList: " + body.get(0).getImage() );
        Picasso.get().load("http://192.168.43.137/wonderfullrestapi/vendor/images/" + body.get(0).getImage()).into(imgHeader);
    }
}