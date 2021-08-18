package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidfinal.adapter.AdapterCast;
import com.example.androidfinal.database.MoviesDatabase;
import com.example.androidfinal.database.MoviesReminderDatabase;
import com.example.androidfinal.model.Movies;
import com.example.androidfinal.receiver.ReminderBroadcast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailMovies extends AppCompatActivity {

    public static List<Movies> listMovieReminder = new ArrayList<>();
    public static final String KEY_PASS_MOVIES = "KEY_PASS_MOVIES";

    public static final String MY_LIST_MOVIE_REMINDER = "list reminder";
    public static final String KEY_GET_LIST_OBJECT_REMINDER = "key get list reminder";


    private TextView mTvReleaseDate, mTvRating, mTvAdult, mTvOverview, mTvTimeReminder;
    private Button mBtnReminder;
    private ImageView mImgFavorite, mImgMovie;
    private Calendar mCalendar;
    private RecyclerView mRecyclerViewCast;
    private AdapterCast mCastAdapter;
    private Movies mMoviesGetIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        init();

        Intent intent = getIntent();
        if(intent != null){
            mMoviesGetIntent = (Movies) intent.getSerializableExtra(KEY_PASS_MOVIES);
        }
        getSupportActionBar().setTitle(mMoviesGetIntent.getMovieTitle()); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar


        mCastAdapter = new AdapterCast(mMoviesGetIntent.getListCast());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailMovies.this
                                                   , LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCast.setLayoutManager(horizontalLayoutManager);
        mRecyclerViewCast.setAdapter(mCastAdapter);

        mTvReleaseDate.setText("Release date: "+mMoviesGetIntent.getMovieReleaseDate());
        mTvRating.setText("Rating: " + mMoviesGetIntent.getMovieRating() + "/10");
        mTvOverview.setText("Description: \n" + mMoviesGetIntent.getMovieOverView());

        setImgFavoriteDetail();
        mTvAdult.setText(mMoviesGetIntent.getMovieType());
        Glide.with(mImgMovie).load(mMoviesGetIntent.getImgPoster()).into(mImgMovie);
        mBtnReminder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                listMovieReminder.add(mMoviesGetIntent);
                Toast.makeText(DetailMovies.this, "Reminder set!", Toast.LENGTH_SHORT).show();
                if(isMoviesExit(mMoviesGetIntent)){
                    Toast.makeText(DetailMovies.this, "the movie has been repeated", Toast.LENGTH_SHORT).show();
                }else{
                    MoviesReminderDatabase.getInstance(DetailMovies.this).moviesDAO().insertMovies(mMoviesGetIntent);
                }
            }
        });
        mTvTimeReminder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                chooseTimeReminder();
            }
        });
    }

    public void init(){
        mTvReleaseDate = findViewById(R.id.activity_detail_movies_tv_date);
        mTvRating = findViewById(R.id.activity_detail_movies_tv_rating);
        mTvAdult = findViewById(R.id.activity_detail_movies_tv_adult);
        mTvOverview = findViewById(R.id.activity_detail_movies_tv_overview);
        mTvTimeReminder = findViewById(R.id.activity_detail_movies_tv_time_reminder);
        mBtnReminder = findViewById(R.id.activity_detail_movies_btn_reminder);
        mImgMovie = findViewById(R.id.activity_detail_movies_img_main);
        mImgFavorite = findViewById(R.id.activity_detail_movies_img_favorite);
        mRecyclerViewCast = findViewById(R.id.activity_detail_movies_rcy);
    }

    private void setImgFavoriteDetail(){
        if(mMoviesGetIntent.isMovieFavorite()){
            mImgFavorite.setImageResource(R.drawable.star);
        }else{
            mImgFavorite.setImageResource(R.drawable.star2);
        }
        mTvAdult.setText(mMoviesGetIntent.getMovieType());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chooseTimeReminder(){
        final Date[] value = {new Date()};
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(value[0]);
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        mCalendar.set(Calendar.YEAR, y);
                        mCalendar.set(Calendar.MONTH, m);
                        mCalendar.set(Calendar.DAY_OF_MONTH, d);

                        // now show the time picker
                        new TimePickerDialog(DetailMovies.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int h, int min) {
                                        mCalendar.set(Calendar.HOUR_OF_DAY, h);
                                        mCalendar.set(Calendar.MINUTE, min);
                                        value[0] = mCalendar.getTime();
                                        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd-HH:mm", Locale.getDefault());
                                        mTvTimeReminder.setText(formatDate.format(mCalendar.getTime()));
                                        mMoviesGetIntent.setTimeReminder(mCalendar.getTimeInMillis());
                                        Log.d("TAG", "onTimeSet: " + mCalendar.getTimeInMillis() + ":" + System.currentTimeMillis());
                                        mMoviesGetIntent.setTimeReminderDisplay(formatDate.format(mCalendar.getTime()));
                                    }
                                }, mCalendar.get(Calendar.HOUR_OF_DAY),
                                mCalendar.get(Calendar.MINUTE), true).show();
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }



    private boolean isMoviesExit(Movies mv){
        List<Movies> list = MoviesReminderDatabase.getInstance(this).moviesDAO().checkMovies(mv.getMovieTitle());
        return list!=null && !list.isEmpty();
    }

}