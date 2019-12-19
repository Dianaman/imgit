package com.example.imgit.gallery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.imgit.R;
import com.example.imgit.album.AlbumActivity;

public class GalleryDialog extends DialogFragment {
    AlbumActivity activity;

    public GalleryDialog(AlbumActivity activity) {
        this.activity = activity;
    }


}
