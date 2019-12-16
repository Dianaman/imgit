package com.example.imgit.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;

import org.json.JSONObject;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    List<JSONObject> albumes;
    GalleryActivity activity;

    public GalleryAdapter(GalleryActivity activity, List<JSONObject> albumes){
        this.activity = activity;
        this.albumes = albumes;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album, parent, false);
        GalleryViewHolder holder = new GalleryViewHolder(v, this.activity);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.position = position;
        holder.setItem(this.albumes.get(position));
    }

    @Override
    public int getItemCount() {
        return this.albumes.size();
    }
}
