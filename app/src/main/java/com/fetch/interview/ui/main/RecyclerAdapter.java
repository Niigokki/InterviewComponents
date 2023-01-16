package com.fetch.interview.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.interview.MainActivity;
import com.fetch.interview.R;
import com.fetch.interview.fetchObject;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    //protected Context con;
    private fetchObject[] dataSet;
    RecyclerView mRecyclerView;
    Context context;

    public RecyclerAdapter(fetchObject[] dataSetFromJSON, Context context) {
        dataSet = dataSetFromJSON;
        this.context = context;
        //notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_template, parent, false);
        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.listID.setText(String.valueOf(dataSet[position].getListID()));
        holder.ID.setText(String.valueOf(dataSet[position].getId()));
        holder.name.setText(String.valueOf(dataSet[position].getName()));
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
    public void setupView() {
        //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }


    protected class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView listID;
        protected TextView ID;
        protected TextView name;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.listID = (TextView) itemView.findViewById(R.id.list_id);
            this.ID = (TextView) itemView.findViewById(R.id.id);
            this.name = (TextView) itemView.findViewById(R.id.name);

        }
        public TextView getListID() {
            return listID;
        }
        public TextView getID() {
            return ID;
        }
        public TextView getName() {
            return name;
        }
        public int getItemCount() {
            return dataSet.length;
        }

    }

}