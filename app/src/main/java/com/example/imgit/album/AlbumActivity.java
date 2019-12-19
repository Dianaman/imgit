package com.example.imgit.album;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.Constants;
import com.example.imgit.R;
import com.example.imgit.camera.SubirFotoThread;
import com.example.imgit.camera.TomarFotoListener;
import com.example.imgit.gallery.GalleryActivity;
import com.example.imgit.gallerynew.GalleryNewDialog;
import com.example.imgit.gallerynew.GalleryNewThread;
import com.example.imgit.image.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements Handler.Callback {
    List<ImageItem> fotos = new ArrayList<ImageItem>();
    Handler handler;
    View v;
    String albumId;
    String albumTitle;
    String albumHash;

    AlbumAdapter adapter;

    Handler newAlbumHandler;
    Handler subirFotoHandler;

    private static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);


        Intent i = this.getIntent();
        Bundle bundle = i.getExtras();
        this.albumId = bundle.getString("id");
        this.albumHash = bundle.getString("hash");
        this.albumTitle = bundle.getString("title");

        Log.d("id", this.albumId);
        Log.d("hash", this.albumHash);
        Log.d("title", this.albumTitle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(this.albumTitle);
        actionBar.setDisplayHomeAsUpEnabled(false);

        SharedPreferences sp = this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

        this.handler = new Handler(this);
        AlbumThread thread = new AlbumThread(this.handler, albumId, sp);
        thread.start();


        TomarFotoListener tomarFotoListener = new TomarFotoListener(this);
        Button tomarFotoBtn = (Button) this.findViewById(R.id.btn_add_photo);
        tomarFotoBtn.setOnClickListener(tomarFotoListener);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if(message.getTarget() == this.handler) {
            this.fotos = (List<ImageItem>) message.obj;

            Log.d("hay fotos", this.fotos.size() + "");


            RecyclerView rv = (RecyclerView) this.findViewById(R.id.recycleView);
            this.adapter = new AlbumAdapter(this, this.fotos);
            rv.setAdapter(adapter);

            //GridLayoutManager glm = new GridLayoutManager(this, 5);
            //glm.setOrientation(RecyclerView.VERTICAL);
            //rv.setLayoutManager(glm);

            LinearLayoutManager lm = new LinearLayoutManager(this);
            lm.setOrientation(RecyclerView.VERTICAL);
            rv.setLayoutManager(lm);
        } else if(message.getTarget() == this.subirFotoHandler) {
            ImageItem item = (ImageItem) message.obj;
            this.fotos.add(item);
        }



        this.adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.album_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == R.id.btn_add_album) {
            GalleryNewDialog popup = new GalleryNewDialog(this);
            popup.show(this.getSupportFragmentManager(), "main");
        } else if(id == R.id.btn_show_albums) {
            Intent i = new Intent(this, GalleryActivity.class);
            startActivity(i);
        }

        return true;
    }

    public void agregarAlbum(String album) {

        SharedPreferences sp = this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

        this.newAlbumHandler = new Handler(this);
        GalleryNewThread thread = new GalleryNewThread(this.newAlbumHandler, sp, album);
        thread.start();
    }

    public void abrirCamara() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if(pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    TAKE_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null && data.getExtras() != null) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                        SharedPreferences sp = this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
                        String albumId = sp.getString(Constants.SP_ALBUM_ID, "");

                        subirFotoHandler = new Handler(this);
                        SubirFotoThread thread = new SubirFotoThread(subirFotoHandler, imageBitmap, albumHash);
                        thread.start();
                    }
                }
                break;
        }
    }
}
