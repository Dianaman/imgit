package com.example.imgit.album;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.Constants;
import com.example.imgit.image.ImageItem;

import java.util.List;

public class AlbumThread extends Thread {
    private Handler handler;
    private String albumId;
    private String username;

    public AlbumThread(Handler handler, String albumId, SharedPreferences sp){
        this.handler = handler;
        this.albumId = albumId;
        this.username = sp.getString(Constants.SP_ACCOUNT_USERNAME, "");
    }

    @Override
    public void run(){
        AlbumManager manager = new AlbumManager();
        List<ImageItem> items = manager.getAlbumImages(this.albumId, this.username);

        Message mensaje = new Message();
        mensaje.obj = items;
        this.handler.sendMessage(mensaje);
    }
}
