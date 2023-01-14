package com.fetch.interview.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.interview.R;
import com.fetch.interview.fetchObject;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.ViewHolder {
    protected Context con;
    private fetchObject[] dataSet;
    public RecyclerAdapter(@NonNull View itemView, ArrayList<fetchObject> array)
    {
        super(itemView);
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
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_template, viewGroup, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
    }
}