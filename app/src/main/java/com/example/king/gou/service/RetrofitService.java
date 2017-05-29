package com.example.king.gou.service;

import com.example.king.gou.utils.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by king on 2017/5/29.
 */

public class RetrofitService {
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    public static class SingleInstanceHolder {
        private static RetrofitService retrofitService = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return SingleInstanceHolder.retrofitService;

    }

    public RetrofitService() {
        init();
    }

    private void init() {
        initRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
}






















