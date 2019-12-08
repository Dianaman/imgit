package com.example.imgit.album;

import android.os.Handler;

public class AlbumThread extends Thread {
    private Handler handler;

    public AlbumThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(){

    }
}
