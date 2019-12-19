package com.example.imgit.album;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgit.Constants;
import com.example.imgit.R;
import com.example.imgit.image.ImageItem;
import com.example.imgit.image.ImageListener;
import com.example.imgit.image.ImageThread;

public class AlbumViewHolder extends RecyclerView.ViewHolder implements Handler.Callback {
    public View itemView;
    public AlbumActivity activity;
    public ImageItem item;
    public int position;
    public Handler handler;
    public boolean isFetching = false;
    public ImageView imageView;
    private SharedPreferences sp;

    public AlbumViewHolder(@NonNull View itemView, AlbumActivity activity) {
        super(itemView);

        this.itemView = itemView;
        this.activity = activity;

        ImageListener listener = new ImageListener(this);
        this.itemView.setOnClickListener(listener);

        this.imageView = this.itemView.findViewById(R.id.imageView);

        this.sp = this.activity.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void setItem(ImageItem item){
        this.item = item;
        if(this.item.image == null && !isFetching){
            this.isFetching = true;
            this.handler = new Handler(this);
            ImageThread thread = new ImageThread(this.handler, item.imageUrl, this.sp);
            thread.start();
        }
    }


    @Override
    public boolean handleMessage(@NonNull Message message) {
        if(message.getTarget() == this.handler) {
            if(message.obj != null ) {
                byte[] byteArray = (byte[]) message.obj;
                Bitmap imagen = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                item.image = imagen;
                this.imageView.setImageBitmap(imagen);
                this.isFetching = false;
            }

        }
        return false;
    }
}
