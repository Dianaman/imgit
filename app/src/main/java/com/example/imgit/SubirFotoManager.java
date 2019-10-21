package com.example.imgit;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class SubirFotoManager {
    public byte[] subirFoto(Bitmap foto) {
        URL url = null;
        byte[] bytes = new byte[0];

        byte[] bytesImage = this.parseBitmapToByteArray(foto);
        JSONObject json = new JSONObject();
        try {
            json.put("image", bytesImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            url = new URL("https://vgy.me/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            Log.d("conexion abierta", "conexion abierta");
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodeParams(json));
            writer.flush();
            writer.close();
            os.close();

            Log.d("le paso los parametros", json.toString());

            if(connection.getResponseCode() == 200){

                Log.d("status 200", "status 200");
                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();

                Log.d("respuesta", sb.toString());
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
        return bytes;
    }

    public byte[] subirFoto(File imageFile) {
        URL url = null;
        byte[] bytes = new byte[0];
        JSONObject json = new JSONObject();

        try {
            json.put("files", imageFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            url = new URL("https://vgy.me/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            Log.d("conexion abierta", "conexion abierta");
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodeParams(json));
            writer.flush();
            writer.close();
            os.close();

            Log.d("le paso los parametros", json.toString());

            if(connection.getResponseCode() == 200){

                Log.d("status 200", "status 200");
                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();

                Log.d("respuesta", sb.toString());
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
        return bytes;
    }
    private byte[] parseBitmapToByteArray(Bitmap bitmap){
        //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File file = new File(path,"/imagen.JPEG");
        byte[] byteArray = new byte[0];

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byteArray = bos.toByteArray();

//write the bytes in file


        return byteArray;
    }

    private static String encodeParams(JSONObject params) throws Exception {
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

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}
