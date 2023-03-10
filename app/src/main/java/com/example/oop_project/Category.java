package com.example.oop_project;

public class Category {
    private String title, uid;
    private long id, timestamp;
    public Category(){
        this.uid = "";
        this.title = "";
        this.id = 0;
        this.timestamp = 0;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
