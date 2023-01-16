package com.fetch.interview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.concurrent.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.widget.ProgressBar;

import com.fetch.interview.ui.main.MainFragment;
import com.fetch.interview.ui.main.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private RecyclerAdapter customAdapter;
        private Context context;
        private ProgressBar pB;
        private fetchObject[] aux;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //Debug.waitForDebugger();
        context = getBaseContext();
        setContentView(R.layout.fragment_main);
         if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();


            //new jsonParser().getJSONasArray();

        }

        //TODO: Use the ViewModel
        //TPTE < asynctask
        //it's a little messy, but it shouldn't leak memory like asyncTask
        //thread = new ThreadPerTaskExecutor();
        Runnable jsonParser = null;
        //Runnable setupList = null;

        try {
            jsonParser = new jsonParser();
            //setupList = new setupList();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
            Thread thread = new Thread(jsonParser);
            thread.start();
            while (thread.isAlive()) {
                System.out.println("waiting......");
            }
            System.out.println("done!");
            setupViews(aux);

            //all code hereafter is on the first thread, except for parsing and so on.




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
            //System.out.println("begin parsing");
            try {
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
                //System.out.println("reader closed, object array generated");
            } catch (IOException e) {
                //code for loading cached version goes here
                //as well as fallback function/data
            }
            //JSON Sanitization step
            for (int i = 0; i < fOb.length; i++) {
                fetchObject f = fOb[i];
                {
                    if (Objects.equals(f.getName(), "") || f.getName() == null) {
                        //System.out.println("ignoring nameless object at index " + i);

                    } else {
                        filteredList.add(f);
                        //System.out.println(filteredList.size());
                    }
                }

            }


            //System.out.print("sanitization finished, sorting now");
            fetchObject[] testArray = new fetchObject[0];
            //I wanted to use the lamdba function but evidently less than 1% of devices run Tiramisu
            //I should get a tiramisu device
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                testArray = (filteredList.toArray(fetchObject[]::new));
            } else {
                testArray = filteredList.toArray(new fetchObject[10]);
            }
            aux = sortByListIDS(testArray);
            //RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.listview);


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


    public fetchObject[] sortByListIDS(@NonNull fetchObject[] list)  {
        //sortbyName var adjusts if we sort by name or list id
        //true = name, false = listid
        mergesort(list, true);
        //System.out.println("sorting by name complete");
        mergesort(list, false);
        //System.out.println("sorting by listID complete");
        //new setupList();
         return list;

    }

    private void setupViews(fetchObject[] objects) {
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter customAdapter = new RecyclerAdapter(objects, this);
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);

    }


    private boolean less(@NonNull fetchObject x, fetchObject y, boolean sortbyName) {
        if (sortbyName == true) {
            if ((x.getName().compareTo(y.getName()) < 0)) {
                return true;
            } else if (x.getName().compareTo(y.getName()) >= 0) {
                return false;
            }
        } else if (sortbyName == false) {

            if ((x.getListID() < y.getListID())) {
                return true;
            } else if (x.getListID() >= y.getListID()) {
                return false;

            }
        }
        return false;
    }


    public void mergesort(fetchObject[] a, boolean sortbyName) {
        aux = Arrays.copyOf(a, a.length);
        Arrays.fill(aux, null);
        msort(a, aux, 0, a.length - 1, sortbyName);
        Arrays.fill(aux, null);
        aux = a;
    }

    /**
     * Sorts a[lo..hi] into ascending order using the recursive mergesort algorithm.
     */
    private void msort(fetchObject[] a, fetchObject[] aux, int lo, int hi, boolean sortbyName) {
        if (hi == lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        msort(a, aux, lo, mid, sortbyName);
        msort(a, aux, mid + 1, hi, sortbyName);
        merge(a, aux, lo, mid, hi, sortbyName);
    }

    private void merge(fetchObject[] a, fetchObject[] aux, int lo, int mid, int hi, boolean sortbyName) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i], sortbyName)) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }


    }








