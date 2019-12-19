package com.example.imgit.album;

import android.util.Log;

import com.example.imgit.Constants;
import com.example.imgit.image.ImageItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlbumManager {
    public List<ImageItem> getAlbumImages(String albumId, String username){
        List<ImageItem> imagenes = new ArrayList<ImageItem>();

        try {


            URL url = new URL("https://api.imgur.com/3/account/" + username + "/album/" + albumId);
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

                String imgStrs = sb.toString();
                Log.d("imagenes", imgStrs);

                JSONObject obj = new JSONObject(imgStrs);
                JSONObject jsonObj = obj.getJSONObject("data");
                JSONArray imgArr = jsonObj.getJSONArray("images");
                for(int i=0; i< imgArr.length(); i++) {
                    JSONObject image = imgArr.getJSONObject(i);

                    ImageItem iItem = new ImageItem();
                    iItem.imageUrl = image.getString("link");
                    iItem.id = image.getString("id");
                    iItem.title = image.getString("title");
                    imagenes.add(iItem);
                }


            } else {
                Log.d("error get imagenes", connection.getResponseMessage());
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
