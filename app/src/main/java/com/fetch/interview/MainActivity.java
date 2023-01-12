package com.fetch.interview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.*;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.fetch.interview.ui.main.MainFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

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

    
    protected class jsonParser {
        String link = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        Gson gson = new Gson();
        ArrayList listIDs = new ArrayList<fetchObject>();

        protected void getJSONasArray() {
            JsonArray jsonArray = JsonParser.parseString(gson.fromJson(link, String.class)).getAsJsonArray();
            int arrLen = jsonArray.size();
            for (int i = 0; i <= arrLen; i++) {
                System.out.println("testing for loop");
                fetchObject temp = new fetchObject();
                JsonElement element = jsonArray.get(i);
                
            }
        }

        }



    }


