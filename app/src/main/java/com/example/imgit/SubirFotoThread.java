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

    @Override
    public void run() {
        SubirFotoManager manager = new SubirFotoManager();
        String resImagen = manager.subirFoto(this.imagenASubir);
        Message message = new Message();
        message.obj = resImagen;
        this.handler.sendMessage(message);

    }
}
