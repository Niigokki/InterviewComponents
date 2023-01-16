package com.fetch.interview.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetch.interview.MainActivity;
import com.fetch.interview.R;
import com.fetch.interview.fetchObject;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    //RecyclerView fraglistview;
    //public RecyclerAdapter customAdapter;
    //ViewGroup container = newInstance().container;
    //public fetchObject[] aux = new fetchObject[5000];
    //public fetchObject placeholder = new fetchObject();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //frags wait for no one now apparently.
        //debug line only
       // Debug.waitForDebugger();
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //fraglistview.setAdapter(new RecyclerAdapter(aux));
        //thread.execute(setupList);



    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }





}