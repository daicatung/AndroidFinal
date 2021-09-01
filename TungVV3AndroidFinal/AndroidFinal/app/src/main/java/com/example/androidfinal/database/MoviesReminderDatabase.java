package com.example.androidfinal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidfinal.model.Movies;

@Database(entities = {Movies.class}, version = 1)

public abstract class MoviesReminderDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="movies.reminder.db";
    private static MoviesReminderDatabase instance;

    public static synchronized MoviesReminderDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MoviesReminderDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public  abstract MoviesDAO moviesDAO();
}
