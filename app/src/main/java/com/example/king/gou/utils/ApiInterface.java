package com.example.king.gou.utils;

import android.text.TextUtils;

import com.example.king.gou.bean.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by king on 2017/5/29.
 */

public interface ApiInterface {
    //默认的服务器地址
    public final static String HOST = "http://vipfacaiflvbceshi.com";

    //登陆
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/signin")
    Observable<Login> getLogin(
            @Query("AppClient") int num,
            @Query("u") String username,
            @Query("p") String password,
            @Query("ipwd") boolean ipwd,
            @Query("reqkey") String reqkey,
            @Query("t") long time
    );

    //登出
    @Headers("X-Requested-With: XMLHttpRequest")
    @GET("/logout")
    Observable<Object> getSignout();

    //登陆状态查询
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/chat-message")
    Observable<Object> getLoginState();
}
