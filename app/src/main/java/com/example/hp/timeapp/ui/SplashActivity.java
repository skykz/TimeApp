package com.example.hp.timeapp.ui;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.timeapp.auth.LoginActivity;
import com.example.hp.timeapp.auth.NumberActivity;
import com.example.hp.timeapp.auth.PhoneLoginActivity;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends Activity {


    private static final String TAG = "SplashActivity";

    private FirebaseAuth fbAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fbAuth = FirebaseAuth.getInstance();
        user = fbAuth.getCurrentUser();


        if (!isOnline())
        {
            Toast.makeText(getApplicationContext(),"Проблема с интернетом! Проверьте соединение с интернетом",Toast.LENGTH_LONG).show();
        }else {

            if (fbAuth.getCurrentUser() != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, NumberActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        getIntent();
        isOnline();
    }
}
