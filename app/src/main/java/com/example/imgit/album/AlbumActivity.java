package com.example.imgit.album;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(this).inflate(R.layout.album, null);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recycleView);
        AlbumAdapter adapter = new AlbumAdapter(this, this.fotos);
        rv.setAdapter(adapter);

        GridLayoutManager glm = new GridLayoutManager(this, 5);
        rv.setLayoutManager(glm);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        return false;
    }
}
