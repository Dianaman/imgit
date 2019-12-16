package com.example.imgit.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;
import com.example.imgit.album.AlbumActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GalleryActivity extends AppCompatActivity implements Handler.Callback {

    public Handler albumsHandler;
    public Handler newAlbumHandler;
    public GalleryAdapter albumsAdapter;
    public List<JSONObject> albumes;
    GalleryActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // action bar

        setContentView(R.layout.gallery);
        this.activity = this;
        Log.d("galeery", "se creo el activity");


        this.albumsHandler = new Handler(this);
        GalleryThread thread = new GalleryThread(this.albumsHandler, this);
        thread.start();

        GalleryListener listener = new GalleryListener(this);
        Button btnAdd = this.findViewById(R.id.btn_add_album);
        btnAdd.setOnClickListener(listener);
    }

    public void agregarAlbum(String album) {
        this.newAlbumHandler = new Handler(this);
        GalleryNewThread thread = new GalleryNewThread(this.newAlbumHandler, this, album);
        thread.start();
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        if(message.getTarget() == this.albumsHandler) {
            Log.d("handle message", "llego un mensaje");

            this.albumes = (List<JSONObject>) message.obj;

            RecyclerView rv = this.activity.findViewById(R.id.rv_albums);
            this.albumsAdapter = new GalleryAdapter(this, this.albumes);
            rv.setAdapter(this.albumsAdapter);

            LinearLayoutManager lm = new LinearLayoutManager(this);
            lm.setOrientation(RecyclerView.VERTICAL);
            rv.setLayoutManager(lm);

        } else {
            this.albumes.add((JSONObject)message.obj);
            this.albumsAdapter.notifyDataSetChanged();
        }
        return false;
    }

    public void verAlbum(int position) {
        Intent i = new Intent(this, AlbumActivity.class);
        JSONObject album = this.albumes.get(position);
        try {
            String id = album.getString("id");
            i.putExtra("album", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(i);
    }
}
