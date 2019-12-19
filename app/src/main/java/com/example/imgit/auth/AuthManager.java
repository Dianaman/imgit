package com.example.imgit.auth;

import android.content.SharedPreferences;
import android.net.Uri;

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
import java.net.URLConnection;

public class AuthManager {
    public Boolean getRefreshToken(SharedPreferences sp) {
        Boolean refrescoToken = false;
        try {
            String path = "https://api.imgur.com/oauth2/token";
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            /*JSONObject json = new JSONObject();
            json.put("client_id", sp.getString(Constants.IMGUR_CLIENT_ID, ""));
            json.put("client_secret", sp.getString(Constants.IMGUR_SECRET, ""));
            json.put("refresh_token", sp.getString(Constants.SP_REFRESH_TOKEN, ""));
            json.put("grant_type", "refresh_token");*/


            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write("client_id=" + sp.getString(Constants.IMGUR_CLIENT_ID, ""));
            bw.write("&client_secret=" + sp.getString(Constants.IMGUR_SECRET, ""));
            bw.write("&refresh_token=" + sp.getString(Constants.SP_REFRESH_TOKEN, ""));
            bw.write("&grant_type=refresh_token");
            bw.flush();
            bw.close();

            if(conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                String nuevoToken = sb.toString();
                if (nuevoToken != null) {
                    refrescoToken = true;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Constants.SP_ACCESS_TOKEN, nuevoToken);
                    editor.commit();
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (JSONException e) {
            e.printStackTrace();
        }*/


        return refrescoToken;
    }
}
