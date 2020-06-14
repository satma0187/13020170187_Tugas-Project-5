package com.example.wonderfullindonesia.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.adapter.ChooseAdapter;
import com.example.wonderfullindonesia.model.Area;
import com.example.wonderfullindonesia.model.Status;
import com.example.wonderfullindonesia.rest.ApiService;
import com.example.wonderfullindonesia.rest.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseFragment extends Fragment {

    ApiService apiService;
    List<Area> movieList;
    ChooseAdapter chooseAdapter;
    RecyclerView recyclerView;

    public static ChooseFragment newInstance() {
        return new ChooseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
        apiService = ApiUtils.getUserService();
        movieList = new ArrayList<>();
        ((MainActivity)getActivity()).setTitle("Pilih Daerah");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        chooseAdapter = new ChooseAdapter(getContext(),movieList, getFragmentManager());
        recyclerView.setAdapter(chooseAdapter);
        getMovie(view);
        return view;
    }

    private void getMovie(View view) {
        Call<List<Area>> call = apiService.getArea();
        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(@NonNull Call<List<Area>> call,@NonNull Response<List<Area>> response) {
                movieList = response.body();
                Log.d("TAG","Response = "+movieList);
                chooseAdapter.setMovieList(movieList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Area>> call,@NonNull Throwable t) {
                Log.d("TAG","Response = " + t.getMessage());

            }
        });

    }
}