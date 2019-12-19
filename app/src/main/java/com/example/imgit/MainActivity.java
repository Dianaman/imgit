package com.example.imgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.content.Intent;

import com.example.imgit.album.Album;
import com.example.imgit.album.AlbumActivity;
import com.example.imgit.auth.AuthActivity;
import com.example.imgit.auth.AuthThread;
import com.example.imgit.gallerynew.GalleryNewThread;
import com.example.imgit.gallery.GalleryThread;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    private static final int TAKE_PICTURE = 1;
    private static final int AUTH = 2;

    Handler authHandler;
    Handler subirFotoHandler; // TODO mover
    Handler galleryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        this.inicializar();
    }


    public void inicializar() {
        SharedPreferences sp = this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        String accId = sp.getString(Constants.SP_ACCOUNT_ID, "");

        if(accId != "") {
            this.realizarOAuth();
        } else {
            this.authHandler = new Handler(this);
            Thread authThread = new AuthThread(authHandler, this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE));
            authThread.start();
        }

    }

    public void realizarOAuth() {
        Intent i = new Intent(this, AuthActivity.class);
        startActivityForResult(i, AUTH);
    }


    public void verAlbum(Album album) {
        Intent i = new Intent(this, AlbumActivity.class);
        i.putExtra("id", album.getId());
        i.putExtra("title", album.getTitle());
        i.putExtra("hash", album.getHash());
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AUTH:
                //if(resultCode == Activity.RESULT_OK) {
                    Log.d("volvio ok del auth", "volvio ok del auth");
                    SharedPreferences sp =  this.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
                    if (true) {
                        // TODO para cuenta existente que tome el primer album
                        this.galleryHandler = new Handler(this);
                        GalleryThread thread = new GalleryThread(this.galleryHandler, sp);
                        thread.start();
                    } else {
                        // TODO para nueva cuenta que cree un album
                        this.galleryHandler = new Handler(this);
                        GalleryNewThread thread = new GalleryNewThread(this.galleryHandler, sp, "Default");
                        thread.start();
                    }
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if(message.getTarget() == this.subirFotoHandler){
            Log.d("mensaje", message.obj.toString());
        } else if(message.getTarget() == this.authHandler) {
            if(!(Boolean) message.obj) {
                this.realizarOAuth();
            }
        } else if(message.getTarget() == this.galleryHandler) {
            List<Album> albumes = (List<Album>) message.obj;
            this.verAlbum(albumes.get(0));
        }

        return false;
    }

    private void requestPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0
                        );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


    }
}
