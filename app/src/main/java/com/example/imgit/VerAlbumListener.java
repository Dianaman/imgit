package com.example.imgit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.imgit.album.AlbumActivity;

public class VerAlbumListener implements View.OnClickListener {
    private MainActivity activity;

    public VerAlbumListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.verAlbum();
    }
}
