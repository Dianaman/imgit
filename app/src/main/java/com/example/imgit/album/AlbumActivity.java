package com.example.imgit.album;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements Handler.Callback {
    List<ImageItem> fotos = new ArrayList<ImageItem>();
    Handler handler;
    View v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.v = LayoutInflater.from(this).inflate(R.layout.album, null);

        Intent i = this.getIntent();
        Bundle bundle = i.getExtras();
        String albumId = bundle.getString("album");

        this.handler = new Handler(this);
        AlbumThread thread = new AlbumThread(this.handler, albumId);
        thread.start();


    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if(message.getTarget() == this.handler) {
            this.fotos = (List<ImageItem>) message.obj;

            RecyclerView rv = (RecyclerView) this.v.findViewById(R.id.recycleView);
            AlbumAdapter adapter = new AlbumAdapter(this, this.fotos);
            rv.setAdapter(adapter);

            GridLayoutManager glm = new GridLayoutManager(this, 5);
            rv.setLayoutManager(glm);
        }
        return false;
    }
}
