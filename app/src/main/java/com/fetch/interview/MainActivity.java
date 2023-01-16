package com.fetch.interview;

import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.interview.ui.main.MainFragment;
import com.fetch.interview.ui.main.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * written by Morgan Caelyn Smith
 * morgan.k.kaine@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter customAdapter;
    /**
     * The context is actually used so I don't understand why AS is catching it in the lint.
     */
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
        }

          /** TODO: see about migrating this block to the viewmodel if possible
          * TPTE < asynctask
          * *it's a little messy, but it shouldn't leak memory like asyncTask
          * thread = new ThreadPerTaskExecutor();
          * Runnable setupList = null;
          */
         Runnable jsonParser = null;

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
            /**I know that UI thread shouldn't really have to wait like this, but I was having
            //issues getting everything to pass back to the original activity from the frag.
            //for the scope of this problem it's fine, and mergesort plus the input
            //sanitization picks up a lot of slack.
             */
        }
        System.out.println("done!");
        setupViews(aux);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    /**
     * this function was easier to write than I'd anticipated honestly.
     * I left my commandline prints in this function, they do a nice job explaining my thought
     * process. GSON is supposedly one of the slower parsers now, so this could be optimized further
     * with a little refactoring to another lib.
     */
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
                //System.out.println("Connection Successful!");
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
                //as well as fallback function/default data
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


    public fetchObject[] sortByListIDS(@NonNull fetchObject[] list) {
        //sortbyName var adjusts if we sort by name or list id
        //true = name, false = listid
        mergesort(list, true);
        //System.out.println("sorting by name complete");
        mergesort(list, false);
        //System.out.println("sorting by listID complete");
        //new setupList();
        return list;

    }

    /**
     * simply sets up all the recyclerview stuff
     * @param objects - the sorted array must be passed to this function to not have a null error
     */
    private void setupViews(fetchObject[] objects) {
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter customAdapter = new RecyclerAdapter(objects, this);
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);

    }

    /**
     * this function is the only part of the mergesort that requires tweaking for additional
     * attributes.
     */
    private boolean less(@NonNull fetchObject x, fetchObject y, boolean sortbyName) {
        if (sortbyName) {
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
    /**
     * this function handles the overhead for merge sort - managing and cleaning the temp array,
     * as well as passing the result back to a place it can be used by the ui thread
     */
    public void mergesort(fetchObject[] a, boolean sortbyName) {
        aux = Arrays.copyOf(a, a.length);
        Arrays.fill(aux, null);
        msort(a, aux, 0, a.length - 1, sortbyName);
        Arrays.fill(aux, null);
        aux = a;
    }

    /**
     * sorts a[lo..hi] into ascending order using the recursive mergesort algorithm.
     *
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
    /**
     * the meat of the merge function, this is where all the actual comparisons take place.
     */
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








