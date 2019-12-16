package com.example.imgit.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.Constants;
import com.example.imgit.Utils;

public class GalleryNewThread extends Thread {
    Handler handler;
    GalleryActivity activity;
    String nombre;
    String token;
    String username;

    public GalleryNewThread(Handler handler, GalleryActivity activity, String nombre){
        this.activity = activity;
        this.handler = handler;
        this.nombre = nombre;

        this.token = Utils.getToken(this.activity);
        this.username = Utils.getUsername(this.activity);
    }

    @Override
    public void run() {
        super.run();

        GalleryManager manager = new GalleryManager();
        String album = manager.agregarAlbum(this.nombre, this.token);

        Message mensaje = new Message();
        mensaje.obj = album;
        this.handler.sendMessage(mensaje);
    }
}
