package com.example.king.gou.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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

import static com.example.king.gou.ui.LoginActivity.getSessionCookie;

/**
 * Created by king on 2017/5/29.
 */

public class RetrofitService extends HttpEngine {

    public static int API_ID_ERROR = 0;
    public static int API_ID_LOGIN = 1;
    public static int API_ID_LOGINSTATE = 2;
    public static int API_ID_USERAMOUNT = 3;//用户的总金额
    public static int API_ID_USERINFO = 4;//用户的基本信息
    public static int API_ID_NOTICECONTENT = 5;//公告
    public static int API_ID_NOTICECONTENT2 = 6;
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
/*    //登出

    public Observable<Object> getLogOut() {
        return apiInterface.getSignout()
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(@NonNull Object o) throws Exception {
                        return Observable.just(o);
                    }
                }).compose(RxUtils.<Object>rxHelper());

    }*/

    /*  //查看登陆状态
      public Observable<LoginState> getLoginSta(int luid, int uonline, int type, String ids, int gets) {
          return apiInterface.getLoginState(luid, uonline, type, ids, gets)
                  .flatMap(new Function<LoginState, ObservableSource<LoginState>>() {
                      @Override
                      public ObservableSource<LoginState> apply(@NonNull LoginState o) throws Exception {
                          return Observable.just(o);
                      }
                  }).compose(RxUtils.<LoginState>rxHelper());

      }*/
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

    public void LoginState(final DataListener listener,
                           int luid,
                           int uonline,
                           int type,
                           String[] ids,
                           int gets
    ) {
        Call<Object> loginState = apiInterface.getLoginState(luid, uonline, type, ids, gets);
        System.out.println("登录信息=" + luid + " " + uonline + " " + type + " " + ids + " " + gets);
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
                ////   LoginState body = response.body();
                Gson gson = new GsonBuilder().setLenient().create();
                //
                //    listener.onReceivedData(API_ID_LOGINSTATE, body, -1);

                listener.onRequestEnd(API_ID_LOGINSTATE);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    public void Login2(final DataListener listener, int num, String username,
                       String password,
                       boolean ipwd,
                       String reqkey,
                       long time) {
        //  listener.onReceivedData(API_ID_LOGIN, null, API_ID_ERROR);

        Call<Login> logine = apiInterface.getLogine(num, username, password, ipwd, reqkey, time);
        String s = logine.request().toString();
        System.out.println("请求完全体=="+s);
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
    public void GetNotices(final DataListener listener) {
        Call<Object> clone = apiInterface.getNotices().clone();
        listener.onRequestStart(API_ID_NOTICECONTENT);
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

                ArrayList<Object> No = new ArrayList<Object>();
                No = (ArrayList<Object>) response.body();
                List<String[]> NoticeContent = new ArrayList<String[]>();
                for (int i = 0; i < No.size(); i++) {
                    String substring = No.get(i).toString().substring(1, No.get(i).toString().length() - 1);
                    String[] split = substring.split(",");
                    NoticeContent.add(split);
                }
                listener.onReceivedData(API_ID_NOTICECONTENT, NoticeContent, API_ID_ERROR);
                Log.d("获取的公告列表==", NoticeContent.get(1)[1] + "  ");
                listener.onRequestEnd(API_ID_NOTICECONTENT);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取公告内容
    public void getNoticesContent(final DataListener listener, int id) {
        Call<Object> noticesContent = apiInterface.getNoticesContent(id);
        Call<Object> clone = noticesContent.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                listener.onRequestStart(API_ID_NOTICECONTENT2);
                String s = response.body().toString();
                s = s.substring(1, s.length() - 1);
                Log.d("一个公告的内容1===", s + "");
                String rc = s.substring(s.indexOf("rc=") + 3, s.indexOf(","));
                String id = s.substring(s.indexOf("id=") + 3, s.indexOf(", others"));
                String msg = s.substring(s.indexOf("msg=") + 4, s.indexOf(", id"));
                String content = s.substring(s.indexOf("content=") + 8, s.indexOf(", user"));

                Log.d("Content的内容===", content);
                Gson gson = new Gson();
                listener.onReceivedData(API_ID_NOTICECONTENT2, content, API_ID_ERROR);
                // NoticeContent noticeContent = gson.fromJson(s, NoticeContent.class);
                //  Log.d("一个公告的内容2===", noticeContent.getOthers().get(0).getUser() + "");
                listener.onRequestEnd(API_ID_NOTICECONTENT2);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //获取玩法
    public void getGametype(DataListener listener, int id) {
        Call<Object> clone = apiInterface.getGameType(id).clone();
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

    //获取游戏
    public void getGame(DataListener listener, int type, int gid, int tid, int ptid) {
        Call<Object> game = apiInterface.getGame(type, gid, tid, ptid);
        Call<Object> clone = game.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取到的游戏===", response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取用户的余额
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

    //奖金详情
    public void GetPrizeDetails(DataListener listener, int rows, int page, int id, int rid) {
        Call<Object> prizeDetails = apiInterface.getPrizeDetails(rows, page, "grid", "asc", id, rid);
        Call<Object> clone = prizeDetails.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取奖金详情", response.body() + "");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //找回密码
    public void getBackPassWord(String u, String p) {
        Call<Object> backPassword = apiInterface.getBackPassword(u, p);
        Call<Object> clone = backPassword.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //找回密码保存
    public void getBackPassWordSave(final DataListener listener, String u, String p) {
        Call<Object> clone = apiInterface.getBackPasswordSave(u, p).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //领取活动奖金
    public void getActivityCheck(DataListener listener, int id, int alid) {
        Call<Object> clone = apiInterface.getActivityCheck(id, alid).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }
}























