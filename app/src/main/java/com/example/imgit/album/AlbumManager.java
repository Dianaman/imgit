package com.example.imgit.album;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import com.example.imgit.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlbumManager {
    public List<ImageItem> getAlbumImages(String albumId){
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

                JSONObject obj = new JSONObject(imgStrs);
                JSONObject jsonObj = obj.getJSONObject("data");
                ImageItem iItem = new ImageItem();
                iItem.imageUrl = jsonObj.getString("link");
                iItem.id = jsonObj.getString("id");
                iItem.title = jsonObj.getString("title");
                imagenes.add(iItem);

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

    public byte[] getImage(String urlPath){
        byte[] imagen;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty ("Authorization", "Client-ID " + Constants.IMGUR_CLIENT_ID);
            connection.connect();

            if(connection.getResponseCode() == 200) {
                imagen = this.leerStream(connection);

                return imagen;
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private byte[] leerStream(HttpURLConnection conexion){
        byte[] byteArray = null;

        try {
            InputStream is = conexion.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1000];
            int cantidadLeida;

            while((cantidadLeida = is.read(buffer, 0, 1000)) > -1){
                baos.write(buffer, 0, cantidadLeida);
            }

            byteArray = baos.toByteArray();

            is.close();

        } catch(IOException e){
            e.printStackTrace();
        }
        return byteArray;
    }
}
