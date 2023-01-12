package com.fetch.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fetch.interview.ui.main.MainFragment;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();

        }
    }
    @Override
    protected void onStart () {
        super.onStart();
    }
    @Override
    protected void onStop () {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    
    protected class getJson {
        String link = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        Gson gson = new Gson();
        int[] listIDs = gson.fromJson(link, int[].class);
    }


}