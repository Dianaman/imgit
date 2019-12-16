package com.example.imgit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    public static String getToken(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return sp.getString(Constants.SP_ACCESS_TOKEN, "");
    }

    public static String getUsername(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return sp.getString(Constants.SP_ACCOUNT_USERNAME, "");
    }
}
