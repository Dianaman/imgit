package com.example.imgit.album;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;
import com.example.imgit.image.ImageItem;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private AlbumActivity activity;
    private List<ImageItem> fotos;

    public AlbumAdapter(AlbumActivity activity, List<ImageItem> fotos){
        this.activity = activity;
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        AlbumViewHolder holder = new AlbumViewHolder(v, this.activity);
        Log.d("create holder", "on creaste viw holder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.position = position;
        holder.setItem(this.fotos.get(position));
        Log.d("Item fetch", this.fotos.get(position).imageUrl);
    }

    @Override
    public int getItemCount() {
        return this.fotos.size();
    }
}
