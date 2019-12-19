package com.example.imgit.gallerynew;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.imgit.R;
import com.example.imgit.album.AlbumActivity;

public class GalleryNewDialog extends DialogFragment {
    AlbumActivity activity;

    public GalleryNewDialog(AlbumActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle b){
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

        View v = LayoutInflater.from(getContext()).inflate(R.layout.gallery_new_popup, null);
        build.setView(v);

        GalleryNewPopupListener listener = new GalleryNewPopupListener(v, this.activity);
        build.setPositiveButton("Crear album", listener);

        AlertDialog ad = build.create();
        return ad;
    }
}
