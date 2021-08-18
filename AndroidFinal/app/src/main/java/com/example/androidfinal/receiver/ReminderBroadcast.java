package com.example.androidfinal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.example.androidfinal.DetailMovies;
import com.example.androidfinal.R;
import com.example.androidfinal.database.MoviesReminderDatabase;
import com.example.androidfinal.model.Movies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReminderBroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Movies movies = (Movies) intent.getSerializableExtra("KEY_PASS_MOVIES");
        if(movies!=null){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
                    .setSmallIcon(R.drawable.test_img)
                    .setContentTitle(movies.getMovieTitle())
                    .setContentText(movies.getMovieReleaseDate())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            notificationManagerCompat.notify(200, builder.build());
            //MoviesReminderDatabase.getInstance(context).moviesDAO().deleteMovies(movies);
        }

    }
}
