package com.example.imgit.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.imgit.R;

public class GalleryPopupListener implements DialogInterface.OnClickListener {
    private View view;
    private GalleryActivity activity;

    public GalleryPopupListener(View view, GalleryActivity activity){
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
