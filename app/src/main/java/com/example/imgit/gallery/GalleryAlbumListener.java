package com.example.imgit.gallery;

import android.view.View;

public class GalleryAlbumListener implements View.OnClickListener {
    GalleryActivity activity;
    int position;

    public GalleryAlbumListener(GalleryActivity activity, int position) {
        this.activity = activity;
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        this.activity.verAlbum(this.position);
    }
}

