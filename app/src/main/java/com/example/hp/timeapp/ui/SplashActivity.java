package com.example.hp.timeapp.ui;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hp.timeapp.auth.LoginActivity;
import com.example.hp.timeapp.auth.PhoneLoginActivity;


public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isOnline())
        {
            Toast.makeText(getApplicationContext(),"Проблема с интернетом! Проверьте соединение с интернетом",Toast.LENGTH_LONG).show();
        }else {

            Intent intent = new Intent(this, PhoneLoginActivity.class);
            startActivity(intent);
            finish();
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
        getIntent();
        isOnline();
    }
}
