package com.example.imgit.gallerynew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.imgit.R;
import com.example.imgit.album.AlbumActivity;

public class GalleryNewPopupListener implements DialogInterface.OnClickListener {
    private View view;
    private AlbumActivity activity;

    public GalleryNewPopupListener(View view, AlbumActivity activity){
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int witch) {
        switch(witch){
            case AlertDialog.BUTTON_POSITIVE:
                EditText editText = this.view.findViewById(R.id.et_gallery_name);
                String texto = editText.getText().toString();
                this.activity.agregarAlbum(texto);
                break;
            default:

        }
    }
}
