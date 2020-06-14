package com.example.wonderfullindonesia.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wonderfullindonesia.R;
import com.example.wonderfullindonesia.activities.DetailActivity;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private String[] mData;
    private Context context;
    private LayoutInflater mInflater;
    private int[] mImgSource;
    private int mIdChoose;
    private int[] numPosition;

    public NavigationAdapter(Context context, String[] data, int[] imgSource, int idChoose, int[] numPosition) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mImgSource = imgSource;
        this.mIdChoose = idChoose;
        this.numPosition = numPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_navigation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imageView.setBackgroundResource(mImgSource[position]);
        holder.textView.setText(mData[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("idChoose", mIdChoose);
                intent.putExtra("code", numPosition[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivNavigation);
            textView = itemView.findViewById(R.id.tvDetail);
        }
    }
}
