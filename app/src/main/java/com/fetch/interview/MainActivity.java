package com.fetch.interview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.fetch.interview.ui.main.MainFragment;
import com.fetch.interview.ui.main.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private RecyclerAdapter customAdapter;
        private Context context;
        private ProgressBar pB;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
         if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();


            //new jsonParser().getJSONasArray();

        }

    }
    @Override
    protected void onStart () {
        super.onStart();
        //setContentView(R.layout.activity_main);

    }
    @Override
    protected void onStop () {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_main);
    }
    }








