package com.example.hp.timeapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.SharedMemory;

import com.example.hp.timeapp.entities.AccessToken;

public class TokenManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;


//    @SuppressLint("CommitPrefEdits")
    private TokenManager(SharedPreferences preferences){

        this.preferences = preferences;
        this.editor = preferences.edit();
    }


    public static synchronized TokenManager getInstance(SharedPreferences preferences){

        if (INSTANCE == null){
            INSTANCE  = new TokenManager(preferences);
        }
        return INSTANCE;
    }

    public void saveToken(AccessToken token){
            editor.putString("ACCESS_TOKEN",token.getAccessToken()).commit();
            editor.putString("REFRESH_TOKEN",token.getRefreshToken()).commit();
    }

    public void deleteToken(){

        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();

    }

    public AccessToken getToken(){
        AccessToken token = new AccessToken();

        token.setAccessToken(preferences.getString("ACCESS_TOKEN",null));
        token.setRefreshToken(preferences.getString("REFRESH_TOKEN",null));

        return token;
    }


}
