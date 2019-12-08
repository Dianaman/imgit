package com.example.imgit;

import android.view.View;


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
