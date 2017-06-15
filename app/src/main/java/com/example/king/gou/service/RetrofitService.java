package com.example.king.gou.service;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.utils.ApiInterface;
import com.example.king.gou.utils.RxUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

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
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    Interceptor myInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //在这里获取到request后就可以做任何事情了
            Response response = chain.proceed(request);
            return response;
        }
    };
    
    private void initRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    //登陆
    public Observable<Login> getLoginInfo(int num, String username,
                                          String password,
                                          boolean ipwd,
                                          String reqkey,
                                          long time) {
        return apiInterface.getLogin(num, username, password, ipwd, reqkey, time)
                .flatMap(new Function<Login, ObservableSource<Login>>() {
                    @Override
                    public ObservableSource<Login> apply(Login o) throws Exception {
                        return Observable.just(o);
                    }
                }).compose(RxUtils.<Login>rxHelper());

    }
    //登出

    public Observable<Object> getLogOut() {
        return apiInterface.getSignout()
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(@NonNull Object o) throws Exception {
                        return Observable.just(o);
                    }
                }).compose(RxUtils.<Object>rxHelper());

    }

    //查看登陆状态
    public Observable<LoginState> getLoginSta(int luid, int uonline, int type, String ids, int gets) {
        return apiInterface.getLoginState(luid, uonline, type, ids, gets)
                .flatMap(new Function<LoginState, ObservableSource<LoginState>>() {
                    @Override
                    public ObservableSource<LoginState> apply(@NonNull LoginState o) throws Exception {
                        return Observable.just(o);
                    }
                }).compose(RxUtils.<LoginState>rxHelper());

    }

    //查询用户余额
    public Observable<Object> getUserAmount() {
        return apiInterface.getUserAmount()
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object o) throws Exception {

                        return Observable.just(o);
                    }
                }).compose(RxUtils.<Object>rxHelper());
    }
}






















