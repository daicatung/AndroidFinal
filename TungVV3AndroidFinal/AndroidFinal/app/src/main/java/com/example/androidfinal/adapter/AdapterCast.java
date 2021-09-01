package com.example.androidfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidfinal.R;
import com.example.androidfinal.model.Cast;

import java.util.List;

public class AdapterCast extends RecyclerView.Adapter<AdapterCast.AdapterCastViewHolder> {

    private List<Cast> mListCast;
    private Context mContext;

    public AdapterCast(List<Cast> list) {
        this.mListCast = list;
    }

    @NonNull
    @Override
    public AdapterCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
       return new AdapterCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCastViewHolder holder, int position) {
        Cast cast = mListCast.get(position);
        holder.setData(cast);
    }

    @Override
    public int getItemCount() {
        return mListCast.size();
    }

    public static class AdapterCastViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImgCast;
        private TextView mTvNameCast;

        public AdapterCastViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgCast = itemView.findViewById(R.id.item_cast_img);
            mTvNameCast = itemView.findViewById(R.id.item_cast_tv_name);
        }

        public void setData(Cast cast){
            Glide.with(mImgCast).load(cast.imgCast).into(mImgCast);
            mTvNameCast.setText(cast.getNameCast());
        }
    }
}
