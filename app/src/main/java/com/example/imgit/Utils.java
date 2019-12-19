package com.example.imgit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class Utils {
    public static String parseBitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(bytesImage, Base64.DEFAULT);


        return encoded;
    }

    public static String encodeParams(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    public static byte[] getImage(String urlPath){
        byte[] imagen;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty ("Authorization", "Client-ID " + Constants.IMGUR_CLIENT_ID);
            connection.connect();

            if(connection.getResponseCode() == 200) {
                imagen = leerStream(connection);
                Log.d("url", urlPath);
                Log.d("bytearr", imagen.toString());
                return imagen;
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] leerStream(HttpURLConnection conexion){
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
