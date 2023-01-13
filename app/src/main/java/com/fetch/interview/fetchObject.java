package com.fetch.interview;

import java.util.Comparator;

public class fetchObject implements Comparator {
    private int listID;
    private String name;
    private int id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public fetchObject() {
        this.listID = 0;
        this.name = "";
        this.id = 0;
    }


    @Override
    public int compare(Object o, Object t1) {

        return 0;
    }
}
