package com.example.imgit;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.imgit.SubirFotoManager.encodeParams;

public class AuthManager {

    private String authURL = "https://api.imgur.com/oauth2/authorize";

    // https://api.imgur.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=REQUESTED_RESPONSE_TYPE&state=APPLICATION_STATE


    public String authorize() {

        String tokenRetorno = "";

        URL url = null;

        try {
            String path = this.authURL + "?";
            path += "client_id=" + Constants.IMGUR_CLIENT_ID;
            path += "&response_type=" + "token";
    Log.d("path", path);

            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();

            if(connection.getResponseCode() == 200) {

                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1000];
                int cantidadLeida = 0;

                while ((cantidadLeida = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, cantidadLeida);
                }

                is.close();
                tokenRetorno = baos.toString();
Log.d("retorno", tokenRetorno);

            } else {
                Log.d("resposne", connection.getResponseMessage());
            }
        } catch(MalformedURLException ex){
            ex.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokenRetorno;
    }
}
