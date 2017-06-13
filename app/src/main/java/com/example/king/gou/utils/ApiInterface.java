package com.example.king.gou.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by king on 2017/5/29.
 */

public interface ApiInterface {
    //默认的服务器地址
    public final static String HOST = "http://vipfacaiflvbceshi.com";
    String text = "AppClient=1&ipwd=false&p=password&u=username&t=1497274998360";

//接口
    /*@GET("/article/list/text")
    Observable<TextEntity> getTexts(@Query("page") int page);*/

    @POST("/signin")
    Observable<Object> getLogin(
            @Query("AppClient") int num,
            @Query("u") String username,
            @Query("p") String password,
            @Query("ipwd") boolean ipwd,
            @Query("reqkey") String reqkey,
            @Query("t") long time
    );


}
