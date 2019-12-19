package com.example.imgit.camera;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.example.imgit.Constants;
import com.example.imgit.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class SubirFotoManager {
    public String subirFoto(Bitmap foto, String albumHash) {
        String respuesta = "";

        try {
            String encoded = Utils.parseBitmapToBase64(foto);

            JSONObject json = new JSONObject();
            json.put("image", encoded);

            URL url = new URL("https://api.imgur.com/3/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty ("Authorization", "Client-ID " + Constants.IMGUR_CLIENT_ID);
            connection.connect();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
            writer.write(Utils.encodeParams(json));
            writer.flush();
            writer.close();
            os.close();

            if(connection.getResponseCode() == 200){

                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();

                respuesta = sb.toString();
                Log.d("imagen subida", respuesta);
                JSONObject obj = new JSONObject(respuesta);
                JSONObject objData = obj.getJSONObject("data");
                String id = objData.getString("id");

                this.subirFotoAAlbum(id, albumHash);
            } else {
                Log.d("error", connection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    private void subirFotoAAlbum(String id, String albumHash) {
        try {
            URL url = new URL("https://api.imgur.com/3/album/" + albumHash + "/add");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Client-ID " + Constants.IMGUR_CLIENT_ID);
            connection.connect();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write("ids[]=" + id);
            writer.flush();
            writer.close();
            os.close();

            if(connection.getResponseCode() == 200){

                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();

                String respuesta = sb.toString();
                Log.d("subio al album", respuesta);
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
    }

}
