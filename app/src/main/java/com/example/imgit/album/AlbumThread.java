package com.example.imgit.album;

import android.os.Handler;
import android.os.Message;

import java.util.List;

public class AlbumThread extends Thread {
    private Handler handler;
    private String albumId;

    public AlbumThread(Handler handler, String albumId){
        this.handler = handler;
        this.albumId = albumId;
    }

    @Override
    public void run(){
        AlbumManager manager = new AlbumManager();
        List<ImageItem> items = manager.getAlbumImages(this.albumId);

        Message mensaje = new Message();
        mensaje.obj = items;
        this.handler.sendMessage(mensaje);
    }
}
