package com.example.androidfinal.ui.movielist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoviesListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MoviesListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is movie list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}