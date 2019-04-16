package com.example.hp.timeapp.networkAPI;


import com.example.hp.timeapp.BuildConfig;
import com.example.hp.timeapp.TokenManager;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {

    private final static String BASE_URL = "https://subshop.kz/api/";

    private final static  OkHttpClient client = buildClient();

    private final static  Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient(){


        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();

                    Request.Builder builder = request.newBuilder()
                            .addHeader("Accept","application/json")
                            .addHeader("Connection","close");

                    request = builder.build();

                    return  chain.proceed(request);
                }
        });

        if (BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service)
    {
        return retrofit.create(service);
    }

    public static <T> T createServiceWithAuth(Class <T> service, final TokenManager tokenManager)
    {
        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if (tokenManager.getToken().getAccessToken() != null)
                {
                    builder.addHeader("Authorization","Bearer " + tokenManager.getToken().getAccessToken());
                }

                request = builder.build();
                return chain.proceed(request);

            }
        }).authenticator(CustomAuth.getInstance(tokenManager)).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return  newRetrofit.create(service);
    }


    public static Retrofit getRetrofit(){
        return retrofit;
    }



}
