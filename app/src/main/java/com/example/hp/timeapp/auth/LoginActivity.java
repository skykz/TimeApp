package com.example.hp.timeapp.auth;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.hp.timeapp.ui.MainActivity;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.util.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.hp.timeapp.entities.AccessToken;
import com.example.hp.timeapp.entities.ApiError;

import  com.example.hp.timeapp.networkAPI.ApiService;
import  com.example.hp.timeapp.networkAPI.RetrofitBuilder;
import com.github.jorgecastilloprz.FABProgressCircle;

import static com.example.hp.timeapp.util.Constants.USER_DATA;
import static com.example.hp.timeapp.util.Utils.convertErrors;


import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";



    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etSurname)
    EditText etSurname;

    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

//    AwesomeValidation validation;
//    Call<AccessToken> call;

    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Slide enterSlide = new Slide(RIGHT);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.llphone);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(RIGHT);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.llphone);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);


        sharedPreferences  = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

    }

//
//    private void showLoading(){
//        TransitionManager.beginDelayedTransition(container);
//        formContainer.setVisibility(View.GONE);
//        loader.setVisibility(View.VISIBLE);
//    }
//    private void showForm(){
//        TransitionManager.beginDelayedTransition(container);
//        formContainer.setVisibility(View.VISIBLE);
//        loader.setVisibility(View.GONE);
//    }

//    void saveData(String user_uid,String phoneNumber) {
//        SharedPreferences.Editor sPref = sharedPreferences.edit();
//
//        sPref.putString("user_uid", user_uid);
//        sPref.putString("user_phone_number",phoneNumber);
//        sPref.commit();
//
//        Toast.makeText(this, "USer data saved", Toast.LENGTH_SHORT).show();
//    }


    @OnClick(R.id.fabProgressCircle)
    void nextActivity() {
        etName.setCursorVisible(false);
        etSurname.setCursorVisible(false);

        rootFrame.setAlpha(0.4f);
        fabProgressCircle.show();

        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etSurname.getWindowToken(), 0);


        String first_name = etName.getText().toString();
        String last_name = etSurname.getText().toString();
        SharedPreferences.Editor sPref = sharedPreferences.edit();

        sPref.putString("first_name", first_name);
        sPref.putString("last_name",last_name);
        sPref.commit();

        Toast.makeText(this, "USer Name is saved" , Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 700);
    }


    @OnClick(R.id.ivback)
    void back() {
        onBackPressed();
    }

//    @OnClick(R.id.fabProgressCircle)
//    void login(){
//
//        validation.clear();
//
//        if (validation.validate()) {


//            call = apiService.login(email, password);
//            call.enqueue(new Callback<AccessToken>() {
//                @Override
//                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//
//                    Log.w(TAG, "onResponse: " + response);
//                    if (response.isSuccessful()) {
//
//                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                        finish();
//
//                    } else {
//                        if (response.code() == 422) {
//                            handleErrors(response.errorBody());
//                        }
//                        if (response.code() == 401) {
//                            ApiError apiError = Utils.convertErrors(response.errorBody());
//                            Toast.makeText(LoginActivity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
//
//                        }
//                        showForm();
//                    }
//                }
//                @Override
//                public void onFailure(Call<AccessToken> call, Throwable t) {
//                    Log.w(TAG, "onFailure: " + t.getMessage());
//                    showForm();
//                }
//            });
//        }
//    }

//    public void setupRules(){
//
//        validation.addValidation(this,R.id.til_name, Patterns.DOMAIN_NAME,R.string.error_invalid_email);
//        validation.addValidation(this,R.id.til_surname,"[a-zA-Z0-9]{6,}",R.string.error_invalid_password);
//
//    }
//    private void handleErrors(ResponseBody responseBody)
//    {
//        ApiError apiError = Utils.convertErrors(responseBody);
//
//
//        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()){
//            if (error.getKey().equals("username"))
//            {
//                tilName.setError(error.getValue().get(0));
//            } if (error.getKey().equals("password"))
//            {
//                tilSurname.setError(error.getValue().get(0));
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


//    @OnClick(R.id.go_to_register)
//    void goToRegister(){
//        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
//    }

}