package com.example.imgit.image;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.Constants;
import com.example.imgit.Utils;

public class ImageThread extends Thread {
    private String imgUrl;
    private Handler handler;
    private String token;

    public ImageThread(Handler handler, String imgUrl, SharedPreferences sp) {
        this.handler = handler;
        this.imgUrl = imgUrl;
        this.token = sp.getString(Constants.SP_ACCESS_TOKEN, "");
    }

    @Override
    public void run() {
        byte[] byteArr = Utils.getImage(this.imgUrl);

        Message mensaje = new Message();
        mensaje.obj = byteArr;
        this.handler.sendMessage(mensaje);
    }
}
