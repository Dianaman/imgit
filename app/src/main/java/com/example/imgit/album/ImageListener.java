package com.example.imgit.album;

import android.view.View;

public class ImageListener implements View.OnClickListener {
    private AlbumViewHolder holder;

    public ImageListener(AlbumViewHolder holder){
        this.holder = holder;
    }

    @Override
    public void onClick(View view) {
        // show dialog photo
    }
}
