package com.example.hp.timeapp.networkAPI;



import com.example.hp.timeapp.entities.AccessToken;
import com.example.hp.timeapp.entities.FreeServicesResponse;
import com.example.hp.timeapp.entities.GetServiceById;
import com.example.hp.timeapp.entities.SingleOrganization;
import com.example.hp.timeapp.entities.UserData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("register") //http://127.1.1.1:8888/api/register
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);


    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login (@Field("username") String username,@Field("password") String password);

    @POST("logout")
    @FormUrlEncoded
    Call<AccessToken> logout (@Field("username") String username,@Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh (@Field("refresh_token") String refreshToken);

    @GET("organizations")
    Call<FreeServicesResponse> freeServices();

    @GET("organizations/{id}")
    Call<SingleOrganization> getServices(@Path("id") int id);

    @POST("authorization")
    Call<UserData> setUserData(@Field("first_name") String first_name, @Field("last_name") String last_name);


}
