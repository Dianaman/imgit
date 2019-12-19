package com.example.imgit.gallerynew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.Constants;
import com.example.imgit.Utils;
import com.example.imgit.album.Album;
import com.example.imgit.gallery.GalleryManager;

import java.util.ArrayList;
import java.util.List;

public class GalleryNewThread extends Thread {
    Handler handler;
    SharedPreferences sp;
    String nombre;
    String token;
    String username;

    public GalleryNewThread(Handler handler, SharedPreferences sp, String nombre){
        this.sp = sp;
        this.handler = handler;
        this.nombre = nombre;

        this.token = sp.getString(Constants.SP_ACCESS_TOKEN, "");
        this.username = sp.getString(Constants.SP_ACCOUNT_USERNAME, "");
    }

    @Override
    public void run() {
        super.run();

        GalleryManager manager = new GalleryManager();
        String album = manager.agregarAlbum(this.nombre, this.token);

        Message mensaje = new Message();
        // TODO
        Album albumCreado = new Album("", "",  album);
        List<Album> albumes = new ArrayList<Album>();
        ((ArrayList) albumes).add(albumCreado);
        mensaje.obj = albumes;
        this.handler.sendMessage(mensaje);
    }
}
