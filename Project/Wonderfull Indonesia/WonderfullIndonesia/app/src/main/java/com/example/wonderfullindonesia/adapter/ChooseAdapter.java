package com.example.wonderfullindonesia.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.activities.HomeFragment;
import com.example.wonderfullindonesia.model.Area;

import java.util.List;

import static java.security.AccessController.getContext;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MovieHolder> {

    Context context;
    List<Area> mMovieArea;
    FragmentManager f_manager;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ChooseAdapter(Context context, List<Area> movieArea, FragmentManager f_manager){
        this.context = context;
        this.mMovieArea = movieArea;
        this.f_manager = f_manager;
    }

    public void setMovieList(List<Area> movieList) {
        this.mMovieArea = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_choose_item,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        holder.tvChoose.setText(mMovieArea.get(position).getArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < 5){
                    f_manager.beginTransaction()
                            .replace(R.id.frame_layout, new HomeFragment())
                            .commit();
                    pref = context.getSharedPreferences("Users", 0);
                    editor = pref.edit();
                    editor.putInt("idChoose", mMovieArea.get(position).getId());
                    editor.apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mMovieArea != null){
            return mMovieArea.size();
        }
        return 0;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        TextView tvChoose;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            tvChoose = itemView.findViewById(R.id.tvChoose);
        }
    }
}
