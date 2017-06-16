package com.example.king.gou.utils;

import android.text.TextUtils;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
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
    Call<Object> getSignout();

    //登陆状态查询
    @Headers({"X-Requested-With: XMLHttpRequest", "Add-Cookie"})

    @POST("/chat-message")
    Call<Object> getLoginState(
            @Query("luid") int luid,
            @Query("uonline") int uonline,
            @Query("type") int type,
            @Query("ids") String[] ids,
            @Query("gets") int gets);

    //查询用户余额
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/amount-refresh")
    Observable<Object> getUserAmount();

    //登陆
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/signin")
    Call<Login> getLogine(
            @Query("AppClient") int num,
            @Query("u") String username,
            @Query("p") String password,
            @Query("ipwd") boolean ipwd,
            @Query("reqkey") String reqkey,
            @Query("t") long time
    );
}
