package com.example.androidfinal.ui.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinal.DetailMovies;
import com.example.androidfinal.R;
import com.example.androidfinal.adapter.AdapterMovie;
import com.example.androidfinal.api.ApiGetCast;
import com.example.androidfinal.api.ApiGetListCast;
import com.example.androidfinal.api.ApiGetListMovie;
import com.example.androidfinal.api.ApiGetMovie;
import com.example.androidfinal.api.ApiGetAllData;
import com.example.androidfinal.database.MoviesDatabase;
import com.example.androidfinal.model.Cast;
import com.example.androidfinal.model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidfinal.adapter.AdapterMovie.checkShow;

public class MoviesListFragment extends Fragment implements View.OnClickListener {

    public static final String BASE_URL = "https://image.tmdb.org/t/p/w200";
    public static final String API_KEY = "e7631ffcb8e766993e5ec0c1f4245f93";
    public static final String KEY_PASS_MOVIE = "KEY_PASS_MOVIES";

    //list display all movie
    public static List<Movies> listMoviesAllApp = new ArrayList<>();

    //list Movie & Cast get from api
    private List<ApiGetMovie> listMovieFromApi;
    private List<ApiGetCast> listCastFromApi;

    private RecyclerView mRecyclerViewDisplayAllMovie, mRecyclerViewDisplayAllMovie2;
    private AdapterMovie mAdapterMovie;

    public static MoviesListFragment newInstance() {
        MoviesListFragment fragment = new MoviesListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // onclick option menu is true click
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies_list, container, false);

        CallApiMovie();

        mRecyclerViewDisplayAllMovie = root.findViewById(R.id.fragment_movies_list_rcy);
        mRecyclerViewDisplayAllMovie2 = root.findViewById(R.id.fragment_movies_list_rcy);

        mAdapterMovie = new AdapterMovie(listMoviesAllApp, new AdapterMovie.onClickItemMovies() {
            @Override
            public void updateFavoriteMovies(Movies mv) {
                updateMovie(mv);
            }
            @Override
            public void deleteFavoriteMovies(Movies mv) {
            }
        }, this, getContext());
        return root;
    }
    private void CallApiMovie() {
        ApiGetAllData.API_GET_ALL_DATA.getMovieApi(API_KEY, 1).enqueue(
                new Callback<ApiGetListMovie>() {
                    @Override
                    public void onResponse(Call<ApiGetListMovie> call, Response<ApiGetListMovie> response) {
                        ApiGetListMovie l = response.body();
                        listMovieFromApi =  l.getResults();

                        //Get 20 movie from api
                        for(int i=0; i<20; ++i){
                            String tmp = listMovieFromApi.get(i).getVote_average()+"";
                            String movieType = "No Adult";
                            if(listMovieFromApi.get(i).adult){
                                movieType = "Adult";
                            }
                            listMoviesAllApp.add(new Movies(listMovieFromApi.get(i).getTitle(), R.drawable.test_img
                                    , listMovieFromApi.get(i).getRelease_date(), tmp
                                    , listMovieFromApi.get(i).getOverview(), movieType
                                    , false));
                            listMoviesAllApp.get(i).setImgPoster(BASE_URL+listMovieFromApi.get(i).getBackdrop_path());
                            listMoviesAllApp.get(i).setIdMovie(listMovieFromApi.get(i).getId());
                            List<Cast> listCastTmp = new ArrayList<>();

                            //Call api cast after set data Movie list cast
                            CallApiCast(listCastTmp, i);
                            listMoviesAllApp.get(i).setListCast(listCastTmp);
                        }
                        mRecyclerViewDisplayAllMovie.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        mRecyclerViewDisplayAllMovie.setAdapter(mAdapterMovie);
                    }
                    @Override
                    public void onFailure(Call<ApiGetListMovie> call, Throwable t) {
                    }
                }
        );
    }

    private void CallApiCast(List<Cast> listCastTmp, int index){
        ApiGetAllData.API_GET_ALL_DATA.getCastApi(listMovieFromApi.get(index).getId()).enqueue(new Callback<ApiGetListCast>() {
            @Override
            public void onResponse(Call<ApiGetListCast> call, Response<ApiGetListCast> response) {
                ApiGetListCast l = (ApiGetListCast) response.body();
                listCastFromApi = l.getCast();
                for(int i=0; i<listCastFromApi.size(); ++i){
                    listCastTmp.add(new Cast(listCastFromApi.get(i).getName()
                                            , BASE_URL + listCastFromApi.get(i).getProfile_path()));
                }
            }
            @Override
            public void onFailure(Call<ApiGetListCast> call, Throwable t) {
            }
        });
    }

    // event when clicking on the menu, the recyclerview will change style
    private int count=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_movie:
                if(count==0){
                    checkShow=1;
                    AdapterMovie adapterMovie3 = new AdapterMovie(listMoviesAllApp);
                    mRecyclerViewDisplayAllMovie2.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    mRecyclerViewDisplayAllMovie2.setAdapter(adapterMovie3);
                    ++count;
                    item.setIcon(getResources().getDrawable(R.drawable.menu));
                }else if(count==1){
                    checkShow=0;
                    mAdapterMovie = new AdapterMovie(listMoviesAllApp, new AdapterMovie.onClickItemMovies() {
                        @Override
                        public void updateFavoriteMovies(Movies mv) {
                           updateMovie(mv);
                        }
                        @Override
                        public void deleteFavoriteMovies(Movies mv) {}
                    } , this, getContext());
                    mRecyclerViewDisplayAllMovie.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    mRecyclerViewDisplayAllMovie.setAdapter(mAdapterMovie);
                    --count;
                    item.setIcon(getResources().getDrawable(R.drawable.menu_grid_view));
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
    private void updateMovie(Movies mv){
        if(mv.isMovieFavorite()){
            mv.setMovieFavorite(false);
            MoviesDatabase.getInstance(getContext()).moviesDAO().deleteMovies(mv);
            Toast.makeText(getContext(), "Delete Movies complete", Toast.LENGTH_SHORT).show();
        }else{
            if(isMoviesExit(mv)){
                Toast.makeText(getContext(), "Movies is already list favorite", Toast.LENGTH_SHORT).show();
                return;
            }
            mv.setMovieFavorite(true);
            MoviesDatabase.getInstance(getContext()).moviesDAO().insertUser(mv);
            Toast.makeText(getContext(), "insert completed", Toast.LENGTH_SHORT).show();
        }
        mAdapterMovie.setData(listMoviesAllApp);
    }
    private boolean isMoviesExit(Movies mv){
        List<Movies> list = MoviesDatabase.getInstance(getContext()).moviesDAO().checkMovies(mv.getMovieTitle());
        return list!=null && !list.isEmpty();
    }
}