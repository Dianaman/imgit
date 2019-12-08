package com.example.imgit.album;

import android.util.Log;

import com.example.imgit.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlbumManager {
    public List<ImageItem> getAlbumImages(int albumId){
        List<ImageItem> imagenes = new ArrayList<ImageItem>();

        try {


            URL url = new URL("https://api.imgur.com/3/album/" + albumId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty ("Authorization", "Client-ID " + Constants.IMGUR_CLIENT_ID);
            connection.connect();

            if(connection.getResponseCode() == 200){

                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();

                // TODO tomar imagenes y pasarlo
                String imgStrs = sb.toString();
                Log.d("imagenes", imgStrs);
            } else {
                Log.d("error", connection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagenes;
    }
}
