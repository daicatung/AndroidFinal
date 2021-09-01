package com.example.androidfinal.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidfinal.ui.about.AboutFragment;
import com.example.androidfinal.ui.favorite.FavoriteFragment;
import com.example.androidfinal.ui.movielist.MoviesListFragment;
import com.example.androidfinal.ui.setting.SettingFragment;

public class AdapterViewPage extends FragmentStateAdapter {

    public static final int TAB_INDEX_FIRST = 0;
    public static final int TAB_INDEX_SECOND = 1;
    public static final int TAB_INDEX_THIRD = 2;
    public static final int TAB_INDEX_FOUR = 3;

    public AdapterViewPage(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case TAB_INDEX_FIRST:
                return MoviesListFragment.newInstance();
            case TAB_INDEX_SECOND:
                return FavoriteFragment.newInstance();
            case TAB_INDEX_THIRD:
                return SettingFragment.newInstance();
            case TAB_INDEX_FOUR:
                return AboutFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
