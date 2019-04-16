package com.example.hp.timeapp.auth;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.hp.timeapp.ui.MainActivity;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.util.Utils;
import com.example.hp.timeapp.entities.AccessToken;
import com.example.hp.timeapp.entities.ApiError;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.networkAPI.RetrofitBuilder;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";


    @BindView(R.id.til_name) TextInputLayout tilName;
    @BindView(R.id.til_email) TextInputLayout tilEmail;
    @BindView(R.id.til_password) TextInputLayout tilPassword;


    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation awesomeValidation;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);

        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));

        setupRules();

//        if(tokenManager.getToken().getAccessToken() != null){
//            startActivity(new Intent(RegistrationActivity.this, ContactsContra.class));
//            finish();
//        }

    }

    @OnClick(R.id.btn_register)
    void register()
    {
        String name = tilName.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

        awesomeValidation.clear();

        if (awesomeValidation.validate()) {

            call = service.register(name, email, password);
            call.enqueue(new Callback<AccessToken>() {

                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse " + response);

                    if (response.isSuccessful()) {

                        Log.w(TAG, "onResponse " + response.body());

                        tokenManager.saveToken(response.body());

                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                        finish();

                    } else {
                        handleErrors(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void setupRules(){

        awesomeValidation.addValidation(this,R.id.til_name,RegexTemplate.NOT_EMPTY,R.string.error_field_required);
        awesomeValidation.addValidation(this,R.id.til_email, Patterns.EMAIL_ADDRESS,R.string.error_invalid_email);
        awesomeValidation.addValidation(this,R.id.til_password,"[a-zA-Z0-9]{6,}",R.string.error_invalid_password);


    }
    private void handleErrors(ResponseBody responseBody)
    {
        ApiError apiError = Utils.convertErrors(responseBody);


        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()){
            if (error.getKey().equals("name"))
            {
                tilName.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email"))
            {
                tilEmail.setError(error.getValue().get(0));
            } if (error.getKey().equals("password"))
            {
                tilPassword.setError(error.getValue().get(0));
            }

        }
    }

    @OnClick(R.id.go_to_login)
    void goToRegister(){
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;
        }
    }
}
