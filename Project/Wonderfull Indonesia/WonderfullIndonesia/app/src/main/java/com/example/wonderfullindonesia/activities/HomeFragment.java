package com.example.wonderfullindonesia.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.adapter.ChooseAdapter;
import com.example.wonderfullindonesia.adapter.NavigationAdapter;
import com.example.wonderfullindonesia.adapter.TourAdapter;
import com.example.wonderfullindonesia.model.Maps;
import com.example.wonderfullindonesia.model.Maps;
import com.example.wonderfullindonesia.model.Tour;
import com.example.wonderfullindonesia.model.Tour;
import com.example.wonderfullindonesia.rest.ApiService;
import com.example.wonderfullindonesia.rest.ApiUtils;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    NavigationAdapter navigationAdapter;
    SharedPreferences pref;
    TourAdapter tourAdapter;
    int idChoose;
    RecyclerView recyclerView;
    ApiService apiService;
    MaterialCardView materialCardView;
    ImageView imageMaps;
    List<Tour> movieList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity)getActivity()).setTitle("Dashboard");
        apiService = ApiUtils.getUserService();
        imageMaps = view.findViewById(R.id.imageMaps);
        pref = Objects.requireNonNull(this.getActivity()).getSharedPreferences("Users", 0); // 0 - for private mode
        idChoose = pref.getInt("idChoose", 1);
        Log.e("RAG", "onCreate: " + idChoose );
        rvNavigation(view);
        rvImages(view);
        materialCardView = view.findViewById(R.id.cardView);
        getMaps(view);
        return view;
    }

    private void rvNavigation(View view){
        int[] numPosition = {1,2,3,4,5,6,7,8};
        String[] data = {"Tari", "Suku", "Bahasa", "Kerajinan",
                "Lagu", "Baju", "Senjata", "Makanan"};
        int[] srcImage = {R.drawable.ic_tari, R.drawable.ic_suku, R.drawable.ic_bahasa, R.drawable.ic_kerajinan,
                R.drawable.ic_lagu, R.drawable.ic_pakaian, R.drawable.ic_senjata, R.drawable.ic_makanan};
        RecyclerView recyclerView = view.findViewById(R.id.rvNavigation);
        int numberOfColumns = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        recyclerView.setLayoutManager(gridLayoutManager);
        navigationAdapter = new NavigationAdapter(getContext(), data, srcImage, idChoose, numPosition);
        recyclerView.setAdapter(navigationAdapter);
    }

    private void rvImages(View view){
        movieList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        tourAdapter = new TourAdapter(getContext(),movieList);
        recyclerView.setAdapter(tourAdapter);
        getMovie();
    }

    private void getMovie() {
        Call<List<Tour>> call = apiService.getTour(idChoose);
        call.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tour>> call, @NonNull Response<List<Tour>> response) {
                movieList = response.body();
                if (movieList.size() == 0){
                    materialCardView.setVisibility(View.VISIBLE);
                }
                Log.d("TAG","Response = "+movieList.size());
                tourAdapter.setMovieList(movieList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Tour>> call,@NonNull Throwable t) {
                Log.d("TAG","Response = " + t.getMessage());
                materialCardView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getMaps(View view){
        Call<List<Maps>> call = apiService.getMaps(idChoose);
        call.enqueue(new Callback<List<Maps>>() {
            @Override
            public void onResponse(@NonNull Call<List<Maps>> call, Response<List<Maps>> response) {
                Log.e("RAG", "onResponse: "+ response.body() );
                try{
                    generateDataList(response.body());
                }catch(Exception e){
                    //Log.e("",e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Maps>> call,@NonNull Throwable t) {
                Log.d("TAG","Response = " + t.getMessage());

            }
        });
    }

    private void generateDataList(List<Maps> body) {
        Log.e("RAG", "generateDataList: " + body.get(0).getImage() );
        Picasso.get().load("http://192.168.43.137/wonderfullrestapi/vendor/images/" + body.get(0).getImage()).into(imageMaps);
    }
}