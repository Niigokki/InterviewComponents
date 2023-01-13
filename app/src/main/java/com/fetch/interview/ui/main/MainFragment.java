package com.fetch.interview.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetch.interview.R;
import com.fetch.interview.fetchObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.concurrent.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        //using another thread for networking, asyncTask has been deprecated for years,
        //it's a little messy
        // TODO: Clean this mess up!
        ThreadPerTaskExecutor thread = new ThreadPerTaskExecutor();
        Runnable jsonParser = null;
        try {
            jsonParser = new jsonParser();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        thread.execute(jsonParser);

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

    static class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    protected class jsonParser implements Runnable {
        URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
        Gson gson = new Gson();
        ArrayList<fetchObject> filteredList = new ArrayList();
        fetchObject[] fOb;
        InputStream iS;

        public jsonParser() throws MalformedURLException {
        }

        protected void getJSONasArray() throws IOException {
            //JsonReader.setLenient(true);
            System.out.println("begin parsing");
            URLConnection connection = url.openConnection();
            iS = connection.getInputStream();
            System.out.println("Connection Successful!");
            InputStreamReader testISR = new InputStreamReader(iS);
            JsonReader reader = new JsonReader(testISR);

            while (reader.hasNext()) {
                 fOb = (gson.fromJson(reader, fetchObject[].class));
                 //System.out.println("" + fOb);
                 //listIDs.add(fOb);
            }
            reader.close();
            System.out.println("reader closed, object array generated");
            for (int i = 0; i < fOb.length; i++) {
                fetchObject f = fOb[i];
                {
                    if (f.getName() == "") {
                        System.out.println("ignoring nameless object at " + i);

                    }
                    else {
                        filteredList.add(f);
                    }

                }
            }


        }

        @Override
        public void run() {
            try {
                getJSONasArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected class jsonString {
        String[] jsonString;

    public String[] getJsonString() {
        return jsonString;
    }
    }
}