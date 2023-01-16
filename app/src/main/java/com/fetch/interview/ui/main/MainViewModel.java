package com.fetch.interview.ui.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fetch.interview.fetchObject;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<fetchObject>> jsonCache;
    //TODO bring over json parser, clean it

    public LiveData<List<fetchObject>> getObjects() {
        if (jsonCache == null){
            jsonCache = new MutableLiveData<List<fetchObject>>();
        }
        return jsonCache;
    }
    private void loadfromCache()
    {   //cache loading code goes here
        //including placeholder

    }
}

