package com.example.androidfinal.ui.favorite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinal.DetailMovies;
import com.example.androidfinal.R;
import com.example.androidfinal.adapter.AdapterMovie;
import com.example.androidfinal.database.MoviesDatabase;
import com.example.androidfinal.model.Movies;
import com.example.androidfinal.ui.about.AboutFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.androidfinal.ui.movielist.MoviesListFragment.listMoviesAllApp;

public class FavoriteFragment extends Fragment implements View.OnClickListener {

    /*
       keyword to shot movies to screen detail movies
     */
    public static final String KEY_PASS_MOVIE = "KEY_PASS_MOVIES";

    private RecyclerView mRecyclerViewMovieFavorite;
    private AdapterMovie mAdapterMovieFavorite;
    private List<Movies> mListMoviesFavorite;
    private EditText mEdtSearchFavorite;


    /*
        fragment initialization
     */
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListMoviesFavorite.clear();
        /*
            Get movie list data from room
         */
        mListMoviesFavorite = MoviesDatabase.getInstance(getContext()).moviesDAO().getListMovies();
        mAdapterMovieFavorite = new AdapterMovie(mListMoviesFavorite, new AdapterMovie.onClickItemMovies() {
            @Override
            public void updateFavoriteMovies(Movies mv) {
                updateMovie(mv);
            }
            @Override
            public void deleteFavoriteMovies(Movies mv) {
                    alertDialogDeleteMovies(mv);
                }
        }, this, getContext());
        mRecyclerViewMovieFavorite.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerViewMovieFavorite.setAdapter(mAdapterMovieFavorite);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        mListMoviesFavorite = new ArrayList<>();
        mRecyclerViewMovieFavorite = root.findViewById(R.id.fragment_favorite_rcy);
        mEdtSearchFavorite = root.findViewById(R.id.fragment_favorite_edt_search);

        mEdtSearchFavorite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Movies> listSearchMovie = new ArrayList<>();
                String str = s.toString();
                for(Movies mv:mListMoviesFavorite){
                    if(mv.getMovieTitle().equals(str)){
                        listSearchMovie.add(mv);
                    }
                }
                mAdapterMovieFavorite = new AdapterMovie(listSearchMovie, new AdapterMovie.onClickItemMovies() {
                    @Override
                    public void updateFavoriteMovies(Movies mv) {
                       updateMovie(mv);
                    }
                    @Override
                    public void deleteFavoriteMovies(Movies mv) {
                        alertDialogDeleteMovies(mv);
                    }

                }, (View.OnClickListener) getContext(), getContext());
                mAdapterMovieFavorite.setData(listSearchMovie);
                mRecyclerViewMovieFavorite.setLayoutManager(new GridLayoutManager(getContext(), 1));
                mRecyclerViewMovieFavorite.setAdapter(mAdapterMovieFavorite);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    mAdapterMovieFavorite = new AdapterMovie(mListMoviesFavorite, new AdapterMovie.onClickItemMovies() {
                        @Override
                        public void updateFavoriteMovies(Movies mv) {
                           updateMovie(mv);
                        }
                        @Override
                        public void deleteFavoriteMovies(Movies mv) {
                           alertDialogDeleteMovies(mv);
                        }
                    });
                    mRecyclerViewMovieFavorite.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    mRecyclerViewMovieFavorite.setAdapter(mAdapterMovieFavorite);
                }
            }
        });
        return root;
    }

    private void updateMovie(Movies mv){
        if(mv.isMovieFavorite()){
            mv.setMovieFavorite(false);
        }else{
            mv.setMovieFavorite(true);
        }
        mAdapterMovieFavorite.setData(mListMoviesFavorite);
    }

    private void alertDialogDeleteMovies(Movies mv){
        new AlertDialog.Builder(getContext())
                .setTitle("ALERT VIEW TITLE")
                .setMessage("Are you sure you want to remove this item from favourite ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mv.setMovieFavorite(false);
                        Toast.makeText(getContext(), "Movies delete completed", Toast.LENGTH_SHORT).show();
                        mListMoviesFavorite.remove(mv);
                        MoviesDatabase.getInstance(getContext()).moviesDAO().deleteMovies(mv);
                        mAdapterMovieFavorite.setData(mListMoviesFavorite);
                    }
                })
                .setNegativeButton("no",null).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_movies_container:
                Movies mv = (Movies) v.getTag();
                Intent intent = new Intent(getContext(), DetailMovies.class);
                intent.putExtra(KEY_PASS_MOVIE, mv);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}