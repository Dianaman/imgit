package com.example.imgit.gallerynew;

import android.view.View;

import com.example.imgit.album.AlbumActivity;

public class GalleryNewListener implements View.OnClickListener {
    AlbumActivity activity;

    public GalleryNewListener(AlbumActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        GalleryNewDialog popup = new GalleryNewDialog(this.activity);
        popup.show(this.activity.getSupportFragmentManager(), "main");
    }
}
