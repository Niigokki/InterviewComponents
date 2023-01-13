package com.fetch.interview.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Debug;
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
import java.util.Comparator;
import java.util.Objects;
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
    private ArrayList<fetchObject> fo1 = new ArrayList();
    private ArrayList<fetchObject> fo2 = new ArrayList();;
    private ArrayList<fetchObject> fo3 = new ArrayList();;
    private ArrayList<fetchObject> fo4 = new ArrayList();;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //frags wait for no one now apparently.
        //Debug.waitForDebugger();
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
                    if (Objects.equals(f.getName(), "") || f.getName() == null) {
                        System.out.println("ignoring nameless object at index " + i);

                    }
                    else {
                        filteredList.add(f);
                        System.out.println(filteredList.size());
                    }
                }

            }
            System.out.print("sanitization finished, sorting now");
            sortByListIDS(filteredList);


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
    public int sortByListIDS(@NonNull ArrayList<fetchObject> list) {
        int j = 0;
        while (j < list.size()) {
            fetchObject f1 = list.get(j);
            if (f1.getListID() ==1) {
                System.out.println("adding entry at " + j + " to list one");
                fo1.add(f1);

            }
            else if (f1.getListID() == 2) {
                System.out.println("adding entry at " + j + " to list two");
                fo2.add(f1);
            }
            else if (f1.getListID() == 3) {
                System.out.println("adding entry at " + j + " to list three");
                fo3.add(f1);
            }
            else if (f1.getListID() == 4) {
                System.out.println("adding entry at " + j + " to list four");

                fo4.add(f1);
            }
            j++;
            //fetchObject f02 = list.get(j);
            //compare(fo1, f02);
        }
        return 0;
    }

}