package com.example.imgit.gallery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;
import com.example.imgit.album.Album;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    GalleryActivity activity;
    View itemView;
    int position;
    TextView tvAlbum;


    public GalleryViewHolder(@NonNull View itemView, GalleryActivity activity) {
        super(itemView);

        this.itemView = itemView;
        this.activity = activity;

        this.tvAlbum = itemView.findViewById(R.id.album_title);
    }

    public void setItem(Album album) {
        this.tvAlbum.setText(album.getTitle());
        GalleryAlbumListener listener = new GalleryAlbumListener(this.activity, this.position);
        this.itemView.setOnClickListener(listener);
    }
}
