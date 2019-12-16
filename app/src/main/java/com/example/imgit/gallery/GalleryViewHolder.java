package com.example.imgit.gallery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    GalleryActivity activity;
    View itemView;
    int position;
    TextView tvName;
    TextView tvId;


    public GalleryViewHolder(@NonNull View itemView, GalleryActivity activity) {
        super(itemView);

        this.itemView = itemView;
        this.activity = activity;
        this.tvName = itemView.findViewById(R.id.albumName);
        this.tvId = itemView.findViewById(R.id.albumId);
    }

    public void setItem(JSONObject album) {
        AlbumListener listener = new AlbumListener(this.activity, this.position);
        this.tvName.setOnClickListener(listener);
        try {
            this.tvName.setText(album.getString("title"));
            this.tvId.setText(album.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
