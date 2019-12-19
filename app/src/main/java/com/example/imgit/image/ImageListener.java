package com.example.imgit.image;

import android.view.View;

import com.example.imgit.album.AlbumViewHolder;

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
