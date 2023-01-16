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
import java.util.concurrent.*;

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
    public RecyclerAdapter customAdapter;
    //ViewGroup container = newInstance().container;
    public fetchObject[] aux = new fetchObject[5000];
    public fetchObject placeholder = new fetchObject();
    ThreadPerTaskExecutor thread;

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

        //TODO: Use the ViewModel
        //TPTE < asynctask
        //it's a little messy, but it shouldn't leak memory like bad implementations of asyncTask
        thread = new ThreadPerTaskExecutor();
        Runnable jsonParser = null;
        Runnable setupList = null;
        try {
            jsonParser = new jsonParser();
            //setupList = new setupList();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        aux[0] = placeholder;
        thread.execute(jsonParser);
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
            sortByListIDS(testArray);
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

    public int sortByListIDS(@NonNull fetchObject[] list)  {
        //sortbyName var adjusts if we sort by name or list id
        //true = name, false = listid
        mergesort(list, true);
        //System.out.println("sorting by name complete");
        mergesort(list, false);
        //System.out.println("sorting by listID complete");
        //new setupList();
        return 0;
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