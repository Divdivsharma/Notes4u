package com.example.satyam.notes4u;

/**
 * Created by Satyam on 25-Oct-17.
 */

public class Note {
    private int id;
    private String title = "";
    private String description = "";

    public Note() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
