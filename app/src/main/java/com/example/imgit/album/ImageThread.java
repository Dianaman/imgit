package com.example.imgit.album;

import android.os.Handler;

public class ImageThread extends Thread {
    private String urlImage;
    private Handler handler;

    public ImageThread(Handler handler, String urlImage) {
        this.handler = handler;
        this.urlImage = urlImage;
    }

    @Override
    public void run() {

    }
}
