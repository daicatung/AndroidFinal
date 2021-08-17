package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.androidfinal.adapter.AdapterMovieReminder;
import com.example.androidfinal.model.Movies;

import static com.example.androidfinal.DetailMovies.listMovieReminder;

public class ShowAllReminder extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_PASS_MOVIES = "KEY_PASS_MOVIES";
    private RecyclerView mRecyclerViewReminder;
    private AdapterMovieReminder mAdapterMovieReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_reminder);

        getSupportActionBar().setTitle("ALL REMINDERS"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        mRecyclerViewReminder = findViewById(R.id.fragment_setting_rcy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapterMovieReminder = new AdapterMovieReminder(listMovieReminder, (View.OnClickListener) ShowAllReminder.this);
        mRecyclerViewReminder.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerViewReminder.setAdapter(mAdapterMovieReminder);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_reminder_container:
                Movies mv = (Movies) v.getTag();
                Intent intent = new Intent(this, DetailMovies.class);
                intent.putExtra(KEY_PASS_MOVIES, mv);
                startActivity(intent);
            default:
        }
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
}