package com.example.imgit.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imgit.Constants;
import com.example.imgit.R;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity {
    AuthorizationRequest authRequest;
    AuthorizationService authService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("en auth", "en create de auth");
        setContentView(R.layout.activity_main);

        AuthorizationServiceConfiguration serviceConfig =
            new AuthorizationServiceConfiguration(
                Uri.parse("https://api.imgur.com/oauth2/authorize"), // authorization endpoint
                Uri.parse("https://api.imgur.com/oauth2/token")); // token endpoint

        AuthorizationRequest.Builder authRequestBuilder =
            new AuthorizationRequest.Builder(
                serviceConfig, // the authorization service configuration
                Constants.IMGUR_CLIENT_ID, // the client ID, typically pre-registered and static
                ResponseTypeValues.TOKEN, // the response_type value: we want a token
                Uri.parse("com.example.imgit://callback")); // the redirect URI to which the auth response is sent
        authRequest = authRequestBuilder.build();

        this.doAuthorization();

    }

    private void doAuthorization() {
        authService = new AuthorizationService(this);
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(authIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            String uriString = data.getData().toString();
            String paramsString = "http://callback?" + uriString.substring(uriString.indexOf("#") + 1);
            Uri uri = Uri.parse(paramsString);

            List<Pair<String, String>> configs = new ArrayList<Pair<String, String>>();

            String accessToken = uri.getQueryParameter(Constants.SP_ACCESS_TOKEN);
            configs.add(new Pair<String, String>(Constants.SP_ACCESS_TOKEN, accessToken));

            String refreshToken = uri.getQueryParameter(Constants.SP_REFRESH_TOKEN);
            configs.add(new Pair<String, String>(Constants.SP_REFRESH_TOKEN, refreshToken));

            String accountUser = uri.getQueryParameter(Constants.SP_ACCOUNT_USERNAME);
            configs.add(new Pair<String, String>(Constants.SP_ACCOUNT_USERNAME, accountUser));

            String accountId = uri.getQueryParameter(Constants.SP_ACCOUNT_ID);
            configs.add(new Pair<String, String>(Constants.SP_ACCOUNT_ID, accountId));

            this.editPrefs(configs);

            this.finish();
        }
    }

    public void editPrefs(List<Pair<String, String>> configs) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(Pair<String, String> config : configs) {
            editor.putString(config.first, config.second);
        }

        editor.commit();
    }

}
