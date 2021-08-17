package com.example.androidfinal.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidfinal.R;
import com.example.androidfinal.model.Movies;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterMovieReminder extends RecyclerView.Adapter<AdapterMovieReminder.AdapterMovieReminderViewHolder> {

    private List<Movies> mListMovieReminder;
    private View.OnClickListener mOnClickListener;

    public AdapterMovieReminder(List<Movies> list, View.OnClickListener onClickListener) {
        this.mListMovieReminder = list;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AdapterMovieReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
      return new AdapterMovieReminderViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AdapterMovieReminderViewHolder holder, int position) {
        Movies movies = mListMovieReminder.get(position);
        holder.setData(movies);
        holder.mContainerLayout.setTag(movies);
        holder.mContainerLayout.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mListMovieReminder.size();
    }

    public static class AdapterMovieReminderViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImgMovie;
        private TextView mTvNameTitle;
        private TextView mTvTimeReal;
        private ConstraintLayout mContainerLayout;
        public AdapterMovieReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgMovie = itemView.findViewById(R.id.item_reminder_img);
            mTvNameTitle = itemView.findViewById(R.id.item_reminder_tv_name);
            mTvTimeReal = itemView.findViewById(R.id.item_reminder_tv_time_real);
            mContainerLayout = itemView.findViewById(R.id.item_reminder_container);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setData(Movies movies){
            Glide.with(mImgMovie).load(movies.getImgPoster()).into(mImgMovie);
            mTvNameTitle.setText(movies.getMovieTitle() + "-" + movies.getMovieReleaseDate() + "-" + movies.getMovieRating()+"/10");
            mTvTimeReal.setText(movies.getTimeReminderDisplay());
        }
    }
}
