package com.fetch.interview.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetch.interview.R;
import com.fetch.interview.fetchObject;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
    RecyclerView fraglistview;
    private ArrayList<fetchObject> fo1 = new ArrayList();
    private ArrayList<fetchObject> fo2 = new ArrayList();;
    private ArrayList<fetchObject> fo3 = new ArrayList();;
    private ArrayList<fetchObject> fo4 = new ArrayList();;
    //private fetchObject[] aux;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //frags wait for no one now apparently.
        Debug.waitForDebugger();
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
        //fraglistview = fraglistview.findViewById(R.id.listview);

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
            fetchObject[] testArray = new fetchObject[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                testArray = (filteredList.toArray(fetchObject[]::new));
            }
            else {
                testArray = filteredList.toArray(new fetchObject[10]);
            }
            sortByListIDS(testArray);


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
    public int sortByListIDS(@NonNull fetchObject[] list) {
        int j = 0;

        mergesort(list);
        /*while (j < list.size()) {
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
        */
        System.out.println("combined/sorting by list id complete");
        return 0;
    }
    public int sortByName(@NonNull ArrayList<fetchObject> list) {
            //fo1.sort();
        int len = list.size();
        int start = 0;
        int mid = start + (len - start)/2;
        return 0;
    }


    private boolean less(@NonNull fetchObject x, fetchObject y) {

       if(x.getName().compareTo(y.getName()) < 0) {
            return true;
       }
       else if (x.getName().compareTo(y.getName()) >= 0) {
           return false;
       }
       else {
           return false;

       }
    }
    public void mergesort(fetchObject[] a) {
        fetchObject[] aux = Arrays.copyOf(a, a.length);
        Arrays.fill(aux, null);
        msort(a, aux, 0, a.length - 1);
        Arrays.fill(aux, null);
    }

    /** Sorts a[lo..hi] into ascending order using the recursive mergesort algorithm. */
    private void msort(fetchObject[] a, fetchObject[] aux, int lo, int hi) {
        if (hi == lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        msort(a,aux,  lo, mid);
        msort(a, aux, mid + 1, hi);
        merge(a, aux,  lo, mid, hi);
    }

    private void merge(fetchObject[] a, fetchObject[] aux, int lo, int mid, int hi) {
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
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            }
            else {
                a[k] = aux[i++];
            }
        }
    }

}