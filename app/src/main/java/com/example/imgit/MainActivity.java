package com.example.imgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import android.content.Intent;

import com.example.imgit.auth.AuthActivity;
import com.example.imgit.camera.SubirFotoThread;
import com.example.imgit.camera.TomarFotoListener;
import com.example.imgit.gallery.GalleryActivity;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    private static final int TAKE_PICTURE = 1;
    private static final int AUTH = 2;

    Handler subirFotoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPermissions();

        TomarFotoListener tomarFotoListener = new TomarFotoListener(this);
        Button tomarFotoBtn = (Button) this.findViewById(R.id.btn_take_photo);
        tomarFotoBtn.setOnClickListener(tomarFotoListener);


        VerAlbumListener verAlbumListener = new VerAlbumListener(this);
        Button verAlbumBtn = (Button) this.findViewById(R.id.btn_view_album);
        verAlbumBtn.setOnClickListener(verAlbumListener);

        Log.d("Antes del login", "antes del login en mainactiity");
        this.login();
    }


    public void login() {
        Intent i = new Intent(this, AuthActivity.class);
        startActivityForResult(i, AUTH);
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

    public void verAlbum() {

        Intent i = new Intent(this, GalleryActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null && data.getExtras() != null) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                        subirFotoHandler = new Handler(this);
                        SubirFotoThread thread = new SubirFotoThread(subirFotoHandler, imageBitmap);
                        thread.start();
                    }
                }
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if(message.getTarget() == this.subirFotoHandler){
            Log.d("mensaje", message.obj.toString());
        } else {
            Log.d("otro mensaje", message.obj.toString());
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
