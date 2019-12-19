package com.example.imgit.gallery;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.imgit.Constants;
import com.example.imgit.Utils;
import com.example.imgit.album.Album;

import org.json.JSONArray;
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

public class GalleryManager {

    public List<Album> traerAlbumes(String token, String username) {
        List<Album> albumes = new ArrayList<Album>();

        try {
            String path = "https://api.imgur.com/3/account/" + username + "/albums";
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty ("Authorization", "Bearer " + token);
            connection.connect();

            if(connection.getResponseCode() == 200){

                BufferedReader in=new BufferedReader( new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                Log.d("albumes", sb.toString());
                String json = sb.toString();

                JSONObject obj = new JSONObject(json);
                JSONArray arrJson = obj.getJSONArray("data");
                for(int i = 0; i < arrJson.length(); i++) {
                    JSONObject albumObj = arrJson.getJSONObject(i);
                    String title = albumObj.getString("title");
                    String hash = albumObj.getString("deletehash");
                    String id = albumObj.getString("id");
                    Album album = new Album(id, hash, title);
                    albumes.add(album);
                }
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
        return albumes;
    }

    public String agregarAlbum(String album, String token) {
        try {
            String path = "https://api.imgur.com/3/album";
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            JSONObject json = new JSONObject();
            json.put("title", album);

            connection.setRequestProperty ("Authorization", "Bearer " + token);
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
                String line = "";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                String res = sb.toString();
                Log.d("nuevo album", res);
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
        return album;
    }
}
