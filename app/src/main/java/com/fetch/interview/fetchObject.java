package com.fetch.interview;

import java.util.ArrayList;

/**
 * simple data structure for parsing our JSON feed.
 * written by Morgan Caelynn Smith.
 */

public class fetchObject {
    private int listId;
    private String name;
    private int id;


    public fetchObject() {
        this.listId = 0;
        this.name = "";
        this.id = 0;
    }

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
        return listId;
    }

    public void setListID(int listID) {
        this.listId = listID;
    }

    public int compare(ArrayList<fetchObject> fetchObjects) {

        return 0;
    }


}
