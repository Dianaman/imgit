package com.example.imgit.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.Constants;

public class GalleryThread extends Thread {
    Handler handler;
    SharedPreferences sp;

    public GalleryThread(Handler handler, SharedPreferences sp) {
        this.handler = handler;
        this.sp = sp;
    }

    @Override
    public void run() {
        super.run();

        String token = sp.getString(Constants.SP_ACCESS_TOKEN, "");
        String username = sp.getString(Constants.SP_ACCOUNT_USERNAME, "");

        GalleryManager vam = new GalleryManager();
        Message mensaje = new Message();
        mensaje.obj = vam.traerAlbumes(token, username);
        this.handler.sendMessage(mensaje);
    }


}
