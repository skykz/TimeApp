package com.example.hp.timeapp.networkAPI;



import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.entities.AccessToken;


import java.io.IOException;


import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class CustomAuth implements Authenticator {

    private static final String TAG = "CUSTOM_activity";
    private TokenManager tokenManager;
    private static CustomAuth INSTANCE;


    private CustomAuth(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    static synchronized CustomAuth getInstance(TokenManager tokenManager){
        if (INSTANCE == null){
            INSTANCE = new CustomAuth(tokenManager);
        }
        return INSTANCE;
    }


    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (responseCount(response) >= 5) {
            return null;
        }

        AccessToken token = tokenManager.getToken();

        ApiService apiService = RetrofitBuilder.createService(ApiService.class);

        Call<AccessToken> call = apiService.refresh(token.getRefreshToken());
        retrofit2.Response<AccessToken> res = call.execute();

            if (res.isSuccessful()) {
                AccessToken newToken = res.body();
                tokenManager.saveToken(newToken);


                return response.request().newBuilder()
                        .header("Authorization", "Bearer " + res.body().getAccessToken())
                        .build();
            } else {
                return null;
            }

    }

    private int responseCount(Response response){
        int result = 1;
        while ((response = response.priorResponse()) != null)
        {
            result++;
        }
        return  result;
    }

}
