package com.example.imgit.camera;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.example.imgit.image.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

public class SubirFotoThread extends Thread {
    private Handler handler;
    private Bitmap imagenASubir;
    private String albumHash;

    public SubirFotoThread(Handler handler, Bitmap imagenASubir, String albumHash) {
        this.handler = handler;
        this.imagenASubir = imagenASubir;
        this.albumHash = albumHash;
    }

    @Override
    public void run() {
        SubirFotoManager manager = new SubirFotoManager();
        String resImagen = manager.subirFoto(this.imagenASubir, this.albumHash);
        Message message = new Message();

        ImageItem item = new ImageItem();
        try {
            JSONObject objRes = new JSONObject(resImagen);
            JSONObject objData = objRes.getJSONObject("data");

            item.imageUrl = objData.getString("link");
            item.id = objData.getString("id");
            item.title = objData.getString("title");
        } catch(JSONException e) {
            e.printStackTrace();
        }

        message.obj = item;
        this.handler.sendMessage(message);

    }
}
