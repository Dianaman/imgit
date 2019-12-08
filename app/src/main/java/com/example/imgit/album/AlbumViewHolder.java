package com.example.imgit.album;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public AlbumActivity activity;
    public ImageItem item;
    public int position;

    public AlbumViewHolder(@NonNull View itemView, AlbumActivity activity) {
        super(itemView);

        this.itemView = itemView;
        this.activity = activity;

        ImageListener listener = new ImageListener(this);
        this.itemView.setOnClickListener(listener);
    }

    public void setItem(ImageItem item){
        this.item = item;
        if(this.item.image == null){
            // TODO buscar imagen
        }
    }
}
