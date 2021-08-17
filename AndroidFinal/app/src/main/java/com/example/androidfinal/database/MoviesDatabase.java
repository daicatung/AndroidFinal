package com.example.androidfinal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidfinal.model.Movies;

@Database(entities = {Movies.class}, version = 1)

public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="movies.db";
    private static MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public  abstract MoviesDAO moviesDAO();
}
