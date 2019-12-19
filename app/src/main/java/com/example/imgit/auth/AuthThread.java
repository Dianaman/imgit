package com.example.imgit.auth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

public class AuthThread extends Thread {
    private Handler handler;
    private SharedPreferences sp;

    public AuthThread(Handler handler, SharedPreferences sp) {
        this.handler = handler;
        this.sp = sp;
    }

    @Override
    public void run() {
        AuthManager manager = new AuthManager();

        Boolean refrescoToken = manager.getRefreshToken(sp);
        Message mensaje = new Message();
        mensaje.obj = refrescoToken;
        this.handler.sendMessage(mensaje);
    }
}
