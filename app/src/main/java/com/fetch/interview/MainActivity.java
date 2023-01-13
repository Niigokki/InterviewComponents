package com.fetch.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fetch.interview.ui.main.MainFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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






