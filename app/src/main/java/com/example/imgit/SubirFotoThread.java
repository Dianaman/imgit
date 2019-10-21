package com.example.imgit;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.io.File;

public class SubirFotoThread extends Thread {
    private Handler handler;
    private Bitmap imagenASubir;
    private File imagePath;

    public SubirFotoThread(Handler handler, Bitmap imagenASubir) {
        this.handler = handler;
        this.imagenASubir = imagenASubir;
    }

    public SubirFotoThread(Handler handler, File imagePath) {
        this.handler = handler;
        this.imagePath = imagePath;
    }

    @Override
    public void run() {
        SubirFotoManager manager = new SubirFotoManager();
        byte[] imagen = manager.subirFoto(this.imagenASubir);
        Message message = new Message();
        message.obj = imagen;
        this.handler.sendMessage(message);

    }
}
