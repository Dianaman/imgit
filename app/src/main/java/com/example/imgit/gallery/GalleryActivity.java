package com.example.imgit.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.imgit.Constants;
import com.example.imgit.R;
import com.example.imgit.album.Album;
import com.example.imgit.album.AlbumActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GalleryActivity extends AppCompatActivity implements Handler.Callback {

    public Handler albumsHandler;
    public Handler newAlbumHandler;
    public GalleryAdapter albumsAdapter;
    public List<Album> albumes;
    GalleryActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);
        this.activity = this;

        SharedPreferences sp = this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

        this.albumsHandler = new Handler(this);
        GalleryThread thread = new GalleryThread(this.albumsHandler, sp);
        thread.start();

    }


    @Override
    public boolean handleMessage(@NonNull Message message) {

        if(message.getTarget() == this.albumsHandler) {
            Log.d("handle message", "llego un mensaje");

            this.albumes = (List<Album>) message.obj;

            RecyclerView rv = this.activity.findViewById(R.id.rv_albums);
            this.albumsAdapter = new GalleryAdapter(this, this.albumes);
            rv.setAdapter(this.albumsAdapter);

            LinearLayoutManager lm = new LinearLayoutManager(this);
            lm.setOrientation(RecyclerView.VERTICAL);
            rv.setLayoutManager(lm);

        } else {
            this.albumes.add((Album) message.obj);
            this.albumsAdapter.notifyDataSetChanged();
        }
        return false;
    }

    public void verAlbum(int position) {
        Intent i = new Intent(this, AlbumActivity.class);
        Album album = this.albumes.get(position);

        String id = album.getId();
        String hash = album.getHash();
        String title = album.getTitle();
        Log.d("id", id);
        Log.d("hash", hash);
        Log.d("title", title);

        i.putExtra("album", id);
        i.putExtra("hash", hash);
        i.putExtra("title", title);
        startActivity(i);
    }
}
