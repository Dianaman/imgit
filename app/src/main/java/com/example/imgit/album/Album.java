package com.example.imgit.album;

public class Album {
    private String id;
    private String hash;
    private String title;

    public Album(String id, String hash, String title) {
        this.id = id;
        this.hash = hash;
        this.title = title;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
