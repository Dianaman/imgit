package com.example.imgit.gallery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.imgit.R;

public class GalleryDialog extends DialogFragment {
    GalleryActivity activity;

    public GalleryDialog(GalleryActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle b){
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        //build.setTitle("Titulo");
        //build.setMessage("Mensaje");

        View v = LayoutInflater.from(getContext()).inflate(R.layout.gallery_popup, null);
        build.setView(v);

        GalleryPopupListener listener = new GalleryPopupListener(v, this.activity);

        build.setPositiveButton("Ok", listener);
        build.setNegativeButton("Cancel", listener);
        build.setNeutralButton("Ajem", listener);

        AlertDialog ad = build.create();
        return ad;
    }
}
