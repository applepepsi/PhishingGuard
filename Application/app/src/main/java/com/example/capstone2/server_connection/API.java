package com.example.capstone2.server_connection;

import okhttp3.Connection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface API {
    @POST("link/post")
    Call<ResponseBody> getLinkResponse(@Body Link link);

    @POST("login/post")
    Call<ResponseBody> getLoginResponse(@Body User user);

    @GET("check/get")
    Call<ResponseBody> getWithBearerToken(@Header("Authorization") String token);

    @POST("register/post")
    Call<ResponseBody> getRegisterResponse(@Body User user);

}