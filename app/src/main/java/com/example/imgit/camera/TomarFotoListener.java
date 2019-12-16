package com.example.imgit.camera;

import android.view.View;

import com.example.imgit.MainActivity;


public class TomarFotoListener implements View.OnClickListener {
    private MainActivity activity;

    public TomarFotoListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        this.activity.abrirCamara();
    }
}
