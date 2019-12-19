package com.example.imgit.camera;

import android.view.View;

import com.example.imgit.MainActivity;
import com.example.imgit.album.AlbumActivity;


public class TomarFotoListener implements View.OnClickListener {
    private AlbumActivity activity;

    public TomarFotoListener(AlbumActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        this.activity.abrirCamara();
    }
}
