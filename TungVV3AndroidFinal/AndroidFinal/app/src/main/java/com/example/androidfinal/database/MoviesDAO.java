package com.example.androidfinal.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidfinal.model.Movies;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert
    void insertMovies(Movies mv);

    @Query("SELECT * FROM movies")
    List<Movies> getListMovies();

    @Query("SELECT * FROM movies where movieTitle=:moviesName")
    List<Movies> checkMovies(String moviesName);

    @Update
    void updateMovies(Movies mv);

    @Delete
    void deleteMovies(Movies mv);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Query("SELECT * FROM movies WHERE movieTitle LIKE '%' || :name || '%' ")
    List<Movies> searchMovies(String name);
}
