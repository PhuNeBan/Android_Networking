package com.example.myapp.helpers;

import com.example.myapp.models.ForgotPWResponseModel;
import com.example.myapp.models.GetNewsDetailModel;
import com.example.myapp.models.GetNewsResponseModel;
import com.example.myapp.models.LoginResponseModel;
import com.example.myapp.models.NewsModel;
import com.example.myapp.models.RegisterResponseModel;
import com.example.myapp.models.UserModel;
import com.example.myapp.models.UserRegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitRouter {
    //http://192.168.10.105:8686/login.php
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/login.php")
    Call<LoginResponseModel> login(@Body UserModel body);

    //http://192.168.10.105:8686/register.php
    @POST("/register.php")
    Call<RegisterResponseModel> register(@Body UserRegisterModel body);

//    http://192.168.10105:8686/get-news.php
    @GET("/get-news.php")
    Call<GetNewsResponseModel> getNews( @Header("Authorization") String token);

    // http://192.168.10.105:8686/get-news-detail.php?news_id=13
    @GET("/get-news-detail.php")
    Call<GetNewsDetailModel> getNewsDetail( @Query("news_id") String news_id);

//    http://192.168.10.105:8686/forgot-password.php
    @POST("/forgot-password.php")
    Call<ForgotPWResponseModel> forgotPassword(@Body UserModel body);
}
