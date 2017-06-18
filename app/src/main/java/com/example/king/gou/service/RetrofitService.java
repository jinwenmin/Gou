package com.example.king.gou.service;


import android.util.Log;

import com.example.king.gou.MyApp;
import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.utils.AddCookiesInterceptor;
import com.example.king.gou.utils.ApiInterface;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.ReceivedCookiesInterceptor;
import com.example.king.gou.utils.RxUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static com.example.king.gou.ui.LoginActivity.getSessionCookie;

/**
 * Created by king on 2017/5/29.
 */

public class RetrofitService extends HttpEngine {

    public static int API_ID_ERROR = 0;//错误的返回
    public static int API_ID_LOGIN = 1;//登录
    public static int API_ID_LOGINSTATE = 2;//检查登录状态
    public static int API_ID_USERAMOUNT = 3;//用户的总金额
    public static int API_ID_USERINFO = 4;

    private Retrofit retrofit;
    private ApiInterface apiInterface;
    String sessionLoginId;

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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();


    }


    public void LogOut() {
        Call<Object> signout = apiInterface.getSignout();
        Call<Object> clone = signout.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                System.out.println("登出的日志==" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    public void LoginUserAmount(final DataListener listener) {
        final Call<UserAmount> userAmount = apiInterface.getUserAmount();
        Call<UserAmount> clone = userAmount.clone();
        listener.onRequestStart(API_ID_USERAMOUNT);
        clone.enqueue(new Callback<UserAmount>() {
            @Override
            public void onResponse(Call<UserAmount> call, retrofit2.Response<UserAmount> response) {
                List<UserAmount> amounts = new ArrayList<UserAmount>();
                amounts.add(0, response.body());
                listener.onReceivedData(API_ID_USERAMOUNT, amounts, API_ID_ERROR);
                Gson gson = new Gson();
                // UserAmount userAmount1 = gson.fromJson(response.body(), UserAmount.class);
                System.out.println(" 用户的余额==" + response.body().toString());
                listener.onRequestEnd(API_ID_USERAMOUNT);
            }

            @Override
            public void onFailure(Call<UserAmount> call, Throwable t) {

            }
        });


    }

    public void LoginState(final DataListener listener,
                           int luid,
                           int uonline,
                           int type,
                           String[] ids,
                           int gets
    ) {

        Call<Object> loginState = apiInterface.getLoginState(luid, uonline, type, ids, gets);
        System.out.println("登录信息=" + luid + " " + uonline + " " + type + " " + ids + " " + gets);
        //    Request.Builder builder = new Request.Builder();
        //   builder.addHeader("Add-Cookie", sessionLoginId);
        Call<Object> clone = loginState.clone();

        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                listener.onRequestStart(API_ID_LOGINSTATE);
                //获取cookie
                String sessionId = getSessionCookie(response.headers().get("Set-Cookie"));

                System.out.println("Cookie登录状态==" + sessionId);
                System.out.println("用户信息登录状态==" + response.body());
                LoginState loginS = new LoginState();
                loginS.setSessionId(sessionId);
                listener.onReceivedData(API_ID_LOGINSTATE, loginS, API_ID_ERROR);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("登录状态错误信息==" + t.toString());
            }
        });


    }

    public void LoginSta(final DataListener listener, int luid, int uonline, int type, String[] ids, int gets) {
        Call<Object> loginState = apiInterface.getLoginState(luid, uonline, type, ids, gets);
        System.out.println("登录信息=" + luid + " " + uonline + " " + type + " " + ids + " " + gets);
        Call<Object> clone = loginState.clone();

        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                listener.onRequestStart(API_ID_LOGINSTATE);
                //获取cookie
                String sessionId = getSessionCookie(response.headers().get("Set-Cookie"));
                System.out.println("用户信息登录状态==" + response.body().toString());
                System.out.println("Cookie登录状态==" + sessionId);
                MyApp.getInstance().setSessionId(sessionId);
                listener.onRequestEnd(API_ID_LOGINSTATE);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //登录
    public void Login2(final DataListener listener, int num, String username,
                       String password,
                       boolean ipwd,
                       String reqkey,
                       long time) {
        //  listener.onReceivedData(API_ID_LOGIN, null, API_ID_ERROR);

        Call<Login> logine = apiInterface.getLogine(num, username, password, ipwd, reqkey, time);
        Call<Login> clone = logine.clone();
        clone.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, retrofit2.Response<Login> response) {
                listener.onRequestStart(API_ID_LOGIN);
                //获取cookie
                sessionLoginId = getSessionCookie(response.headers().get("Set-Cookie"));
                System.out.println("Cookie==" + sessionLoginId);
                Gson gson = new GsonBuilder().setLenient().create();
                Login body = response.body();
                body.setSessionId(sessionLoginId);
                listener.onReceivedData(API_ID_LOGIN, body, -1);
                System.out.println("用户信息==" + body);
                listener.onRequestEnd(API_ID_LOGIN);
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    //用户的基本信息
    public void GetUserInfo(final DataListener listener) {
        Call<UserInfo> userInfo = apiInterface.getUserInfo();
        Call<UserInfo> clone = userInfo.clone();
        clone.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, retrofit2.Response<UserInfo> response) {
                listener.onRequestStart(API_ID_USERINFO);
                List<UserInfo> userInfos = new ArrayList<UserInfo>();
                userInfos.add(0, response.body());
                listener.onReceivedData(API_ID_USERINFO, userInfos, API_ID_ERROR);
                System.out.println("用户的基本信息==" + response.body());
                listener.onRequestEnd(API_ID_USERINFO);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    //获取活动列表
    public void GetActivityList(DataListener listener) {
        Call<Object> activityList = apiInterface.getActivityList();
        Call<Object> clone = activityList.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取活动列表", response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取公告
    public void GetNotices(DataListener listener) {
        Call<Object> clone = apiInterface.getNotices().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                List<String[]> Notice = new ArrayList<String[]>();
                Notice.add(0, new String[]{response.body().toString()});

                Log.d("获取的公告列表==", Notice.get(0)[0] + "  ");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取公告内容
    public void getNoticesContent(DataListener listener, int id) {
        Call<Object> noticesContent = apiInterface.getNoticesContent(id);
        Call<Object> clone = noticesContent.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("一个公告的内容", response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //获取玩法
    public void getGametype(DataListener listener) {
        Call<Object> clone = apiInterface.getGameType().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取游戏玩法", response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }
}






















