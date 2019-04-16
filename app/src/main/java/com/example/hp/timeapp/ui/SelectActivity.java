package com.example.hp.timeapp.ui;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.auth.LoginActivity;
import com.example.hp.timeapp.networkAPI.ApiService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectActivity extends AppCompatActivity {

private final String TAG = "SelectActivity";

    @BindView(R.id.container_select)
    ConstraintLayout container;

    @BindView(R.id.loader_select)
    ProgressBar loader;



    @BindView(R.id.button_user)
    Button getButton_user;

    private ApiService apiService;
    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);

//        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));
//
//        apiService = RetrofitBuilder.createService(ApiService.class);
//
//        if (tokenManager.getToken().getAccessToken() != null)
//        {
//            startActivity(new Intent(SelectActivity.this,MainActivity.class));
//            finish();
//        }

        getButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                Intent go = new Intent(SelectActivity.this, LoginActivity.class);
                startActivity(go);
                finish();
            }
        });


    }

    private void showLoading(){
        TransitionManager.beginDelayedTransition(container);
//        container.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
