package com.example.wonderfullindonesia.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.activities.HomeFragment;
import com.example.wonderfullindonesia.model.Tour;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.MovieHolder> {

    Context context;
    List<Tour> mMovieTour;

    public TourAdapter(Context context, List<Tour> movieTour){
        this.context = context;
        this.mMovieTour = movieTour;
    }

    public void setMovieList(List<Tour> movieList) {
        this.mMovieTour = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_tour_item,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        if (mMovieTour.get(position).getId() != 0){
            Picasso.get().load("http://192.168.43.137/wonderfullrestapi/vendor/images/" + mMovieTour.get(position).getImages()).placeholder(R.drawable.no_images).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mMovieTour != null){
            return mMovieTour.size();
        }
        return 0;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
