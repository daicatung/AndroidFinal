package com.example.androidfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidfinal.adapter.AdapterViewPage;
import com.example.androidfinal.model.Movies;
import com.example.androidfinal.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import static com.example.androidfinal.DetailMovies.listMovieReminder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String KEY_GET_LIST_OBJECT_REMINDER = "key get list reminder";
    public static final String KEY_SHARE_SAVE_DATA = "MyObjectUser";
    private AppBarConfiguration mAppBarConfiguration;
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private AdapterViewPage mViewPagerAdapter;
    //component navigation drawer
    private ImageView mImgAvatar;
    private Button mBtnShowAllReminder, mBtnEditProfile;
    private TextView mTvNameUser, mTvDateUser, mTvEmailUser, mTvSexUser, mTvReminderList1, mTvReminderList2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar_main_tool_bar);
        setSupportActionBar(toolbar);

        mViewPager = findViewById(R.id.content_main__viewpager2_menu);
        mTabLayout = findViewById(R.id.content_main_tab_layout_menu);

        mViewPagerAdapter = new AdapterViewPage(getSupportFragmentManager(), getLifecycle());
        mViewPager.setAdapter(mViewPagerAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case AdapterViewPage.TAB_INDEX_FIRST:
                        tab.setText("Movies List");
                        break;
                    case AdapterViewPage.TAB_INDEX_SECOND:
                        tab.setText("Favorite");
                        break;
                    case AdapterViewPage.TAB_INDEX_THIRD:
                        tab.setText("Setting");
                        break;
                    case AdapterViewPage.TAB_INDEX_FOUR:
                        tab.setText("About");
                        break;
                }
            }
        }).attach();

        mTabLayout.addOnTabSelectedListener(new TabChangeListener(this));

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer_layout);
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.content_main_nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View header = navigationView.getHeaderView(0);
        mBtnShowAllReminder = (Button)header.findViewById(R.id.nav_header_main_btn_show_all);
        mBtnEditProfile = (Button)header.findViewById(R.id.nav_header_main_btn_edit);
        mImgAvatar = (ImageView)header.findViewById(R.id.nav_header_main_img_avatar);
        mTvNameUser = (TextView)header.findViewById(R.id.nav_header_main_tv_name);
        mTvDateUser = (TextView)header.findViewById(R.id.activity_edit_user_tv_date_user);
        mTvEmailUser = (TextView)header.findViewById(R.id.activity_edit_user_tv_email_user);
        mTvSexUser = (TextView)header.findViewById(R.id.activity_edit_user_tv_sex_user);
        mTvReminderList1 = (TextView)header.findViewById(R.id.nav_header_main_tv_mv1);
        mTvReminderList2 = (TextView)header.findViewById(R.id.nav_header_main_tv_mv2);

        mBtnShowAllReminder.setOnClickListener(this);
        mBtnEditProfile.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        if(prefs != null){
            String name = prefs.getString("name", "No name defined");
            String date = prefs.getString("date_user", "No date defined");
            String email = prefs.getString("email_user", "No email defined");
            String gt = prefs.getString("sex_user", "No defined");

            mTvNameUser.setText(name);
            mTvDateUser.setText(date);
            mTvEmailUser.setText(email);
            mTvSexUser.setText(gt);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        if(prefs != null){
            String name = prefs.getString("name_user", "No name defined");
            String date = prefs.getString("date_user", "No date defined");
            String email = prefs.getString("email_user", "No email defined");
            String gt = prefs.getString("sex_user", "No defined");

            mTvNameUser.setText(name);
            mTvDateUser.setText(date);
            mTvEmailUser.setText(email);
            mTvSexUser.setText(gt);
        }


        if(listMovieReminder.size()==1){
            mTvReminderList1.setText(listMovieReminder.get(0).getMovieTitle() + " "
                                    +listMovieReminder.get(0).getMovieReleaseDate() + " "
                                    +listMovieReminder.get(0).getMovieRating()+"/10 +\n"
                                    +listMovieReminder.get(0).getTimeReminderDisplay());
        }else if(listMovieReminder.size()>1){
            mTvReminderList1.setText(listMovieReminder.get(0).getMovieTitle()+ " "
                    + listMovieReminder.get(0).getMovieReleaseDate()+ " "
                    +listMovieReminder.get(0).getMovieRating()+"/10 +\n"
                    +listMovieReminder.get(0).getTimeReminderDisplay());

            mTvReminderList2.setText(listMovieReminder.get(1).getMovieTitle()+ " "
                    + listMovieReminder.get(1).getMovieReleaseDate()+ " "
                    +listMovieReminder.get(1).getMovieRating()+"/10 +\n"
                    +listMovieReminder.get(1).getTimeReminderDisplay());
        }
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // support NavigateUp
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.content_main_nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //onclick view main activity
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_header_main_btn_show_all:
                Intent intent = new Intent(MainActivity.this, ShowAllReminder.class);
                startActivity(intent);
                break;
            case R.id.nav_header_main_btn_edit:
                Intent intent2 = new Intent(MainActivity.this, EditUser.class);
                startActivity(intent2);
                break;
            default:
        }
    }
    //Change tab layout when fragment change
    private static class TabChangeListener implements TabLayout.OnTabSelectedListener {
        private MainActivity mActivity;

        public TabChangeListener(MainActivity mActivity) {
            this.mActivity = mActivity;
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {}

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    }
}