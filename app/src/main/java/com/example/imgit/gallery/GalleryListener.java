package com.example.imgit.gallery;

import android.view.View;

public class GalleryListener implements View.OnClickListener {
    GalleryActivity activity;

    public GalleryListener(GalleryActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        GalleryDialog popup = new GalleryDialog(this.activity);
        popup.show(this.activity.getSupportFragmentManager(), "main");
    }
}
