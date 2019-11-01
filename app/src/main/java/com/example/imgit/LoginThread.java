package com.example.imgit;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class LoginThread extends Thread {
    private Handler handler;

    public LoginThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        AuthManager authManager = new AuthManager();
        String token = authManager.authorize();
        Message message = new Message();
        message.obj = token;
        Log.d("token", token);
        this.handler.sendMessage(message);
    }
}
