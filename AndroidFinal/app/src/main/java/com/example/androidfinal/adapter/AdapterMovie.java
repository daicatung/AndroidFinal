package com.example.androidfinal.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidfinal.R;
import com.example.androidfinal.model.Movies;

import java.io.File;
import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.AdapterMovieViewHolder> {

    public static int checkShow = 0;
    private List<Movies> mListMovies;
    private Context mContext;
    private onClickItemMovies mOnClickItemMovies;
    private View.OnClickListener monClickListener;


    public AdapterMovie(List<Movies> list, AdapterMovie.onClickItemMovies onClickItemMovies) {
        this.mListMovies = list;
        this.mOnClickItemMovies = onClickItemMovies;
    }
    public AdapterMovie(List<Movies> list, AdapterMovie.onClickItemMovies onClickItemMovies, View.OnClickListener onClickListener, Context context) {
        this.mListMovies = list;
        this.mOnClickItemMovies = onClickItemMovies;
        this.monClickListener = onClickListener;
        this.mContext = context;
    }

    public AdapterMovie(List<Movies> list) {
        this.mListMovies = list;
    }

    public interface onClickItemMovies{
        void updateFavoriteMovies(Movies mv);
        void deleteFavoriteMovies(Movies mv);
    }

    public void setData(List<Movies> list){
        this.mListMovies = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (checkShow == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies2, parent, false);
        }

        return new AdapterMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovieViewHolder holder, int position) {
        Movies movies = mListMovies.get(position);
        holder.setData(movies);


        holder.mContainerLayout.setOnClickListener(monClickListener);
        holder.mContainerLayout.setTag(movies);

        if(checkShow==0){
            holder.mImgFavoriteMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItemMovies.updateFavoriteMovies(movies);
                    mOnClickItemMovies.deleteFavoriteMovies(movies);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    public static class AdapterMovieViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvNameMovie;
        private TextView mTvReleaseDateMovie;
        private TextView mTvRatingMovie;
        private TextView mTvDetailMovie;
        private TextView mTvTypeMovie;
        private ImageView mImgMovie;
        private ImageView mImgFavoriteMovie;
        private ConstraintLayout mContainerLayout;

        public AdapterMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvNameMovie = itemView.findViewById(R.id.item_movies_tv_name_movie);
            mTvReleaseDateMovie = itemView.findViewById(R.id.item_movies_tv_release_date_movie);
            mTvRatingMovie = itemView.findViewById(R.id.item_movies_tv_rating_movie);
            mTvDetailMovie = itemView.findViewById(R.id.item_movies_tv_detail_movie);
            mTvTypeMovie = itemView.findViewById(R.id.item_movies_tv_type_movie);
            mImgMovie = itemView.findViewById(R.id.item_movies_img_movie);
            mImgFavoriteMovie = itemView.findViewById(R.id.item_movies_img_favorite_movie);
            if(checkShow == 0){
                mContainerLayout = itemView.findViewById(R.id.item_movies_container);
            }else{
                mContainerLayout = itemView.findViewById(R.id.item_movies2_container);
            }
        }

        public void setData(Movies movies){
            if(checkShow == 0){
                mTvNameMovie.setText(movies.getMovieTitle());
                mTvDetailMovie.setText(movies.getMovieOverView());
                mTvTypeMovie.setText(movies.getMovieType());
                mTvRatingMovie.setText("Rating: " + movies.getMovieRating()+"/10");
                mTvReleaseDateMovie.setText("Date: " + movies.getMovieReleaseDate());
                Glide.with(mImgMovie).load(movies.getImgPoster()).into(mImgMovie);
                if(movies.isMovieFavorite()){
                    mImgFavoriteMovie.setImageResource(R.drawable.star);
                }else{
                    mImgFavoriteMovie.setImageResource(R.drawable.star2);
                }
            }else{
                mTvNameMovie.setText(movies.getMovieTitle());
                Glide.with(mImgMovie).load(movies.getImgPoster()).into(mImgMovie);
            }
        }
    }
}
