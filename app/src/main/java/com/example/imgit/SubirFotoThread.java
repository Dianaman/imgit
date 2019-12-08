package com.example.imgit;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class SubirFotoThread extends Thread {
    private Handler handler;
    private Bitmap imagenASubir;

    public SubirFotoThread(Handler handler, Bitmap imagenASubir) {
        this.handler = handler;
        this.imagenASubir = imagenASubir;
    }

    @Override
    public void run() {
        SubirFotoManager manager = new SubirFotoManager();
        String resImagen = manager.subirFoto(this.imagenASubir);
        Message message = new Message();
        message.obj = resImagen;
        this.handler.sendMessage(message);

    }
}
