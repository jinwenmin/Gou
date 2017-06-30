package com.example.king.gou.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.bean.RestultInfo;

import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.ui.MainActivity;
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
import java.math.BigDecimal;
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
    public static int API_ID_NOTICECONTENT2 = 6;//公告详情
    public static int API_ID_UPDATENICKNAME = 7;//修改用户昵称
    public static int API_ID_SAFEQUES = 8;//获取安全问题
    public static int API_ID_UPDATAPWD = 9;//修改登录密码
    public static int API_ID_SAFEPWD = 10;//验证安全密码
    public static int API_ID_UPDATAFIRSTPWD = 11;//强制修改初始密码
    public static int API_ID_HOMENOTICE = 12;//首页的滚动公告
    public static int API_ID_CARDLOCK = 13;//绑定银行卡锁定
    public static int API_ID_CARDDATAS = 14;//获取绑定银行卡所需要信息
    public static int API_ID_GETCITYS = 15;//省市联动获得的市名
    public static int API_ID_SAVECARD = 16;//保存银行卡
    public static int API_ID_CHATUSERS = 17;//获取聊天用户

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
        System.out.println("请求完全体==" + s);
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
                Log.d("获取的公告列表==", response.body() + "  ");
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
                String time = s.substring(s.indexOf("time=") + 5, s.indexOf(", sticky"));
                String user = s.substring(s.indexOf("user=") + 5, s.indexOf(", time"));
                String content = s.substring(s.indexOf("content=") + 8, s.indexOf(", user"));
                String[] strings = new String[]{content, time, user};
                Log.d("Content的内容===", content);
                Gson gson = new Gson();
                listener.onReceivedData(API_ID_NOTICECONTENT2, strings, API_ID_ERROR);
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

    //修改用户昵称
    public void getUpdateNickName(final DataListener listener, String nickName) {
        Call<RestultInfo> nickNameChange = apiInterface.getNickNameChange(nickName);
        Call<RestultInfo> clone = nickNameChange.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("修改昵称返回的", response.body().getMsg() + "");
                listener.onReceivedData(API_ID_UPDATENICKNAME, response.body(), API_ID_ERROR);
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });


    }

    //强制修改初始密码
    public void getUpdateFirstPwd(final DataListener listener, String p0, String p1, String p2, String email) {
        Call<RestultInfo> clone = apiInterface.getUpdateFirstPwd(p0, p1, p2, email).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                listener.onReceivedData(API_ID_UPDATAFIRSTPWD, response.body(), API_ID_ERROR);
                Log.d("强制修改初始密码的结果", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //获取安全问题
    public void getSafeQues(final DataListener listener) {
        Call<Object> clone = apiInterface.getSafeQues().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String ques = response.body().toString();
                String substring = ques.substring(1, ques.length() - 1);

                String[] split = substring.split(",");
                List<String> Safe = new ArrayList<String>();
                for (int i = 1; i < split.length; i = i + 2) {
                    Log.d("安全问题是==", split[i].substring(0, split[i].length() - 1));
                    Safe.add(split[i].substring(0, split[i].length() - 1));
                }
                listener.onReceivedData(API_ID_SAFEQUES, Safe, API_ID_ERROR);
                Log.d("安全问题是2==", substring);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //获取首页的公告或者滚动栏的内容
    public void getHomeNotice(final DataListener listener) {
        Call<Object> clone = apiInterface.getHomeNotice().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String toString = response.body().toString();
                String substring = toString.substring(toString.indexOf("content=") + 8, toString.indexOf(", user="));
                listener.onReceivedData(API_ID_HOMENOTICE, substring, API_ID_ERROR);
                Log.d("首页的公告==", toString);
                Log.d("首页的公告2==", substring);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //修改登录密码
    public void getUpDataPwd(final DataListener listener, String p0, String p1) {
        Call<RestultInfo> clone = apiInterface.getUpDatePwd(p0, p1).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                listener.onReceivedData(API_ID_UPDATAPWD, response.body(), API_ID_ERROR);
                Log.d("修改密码的结果", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //验证安全密码
    public void getCheckSafePwd(final DataListener listener, String p) {
        Call<RestultInfo> clone = apiInterface.getCheckSafePwd(p).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                listener.onReceivedData(API_ID_SAFEPWD, response.body(), API_ID_ERROR);
                Log.d("验证安全问题的结果", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //保存安全问题
    public void getSaveSafeQus(DataListener listener, String q, String a) {
        Call<RestultInfo> infoCall = apiInterface.getSaveSafeQues(q, a).clone();
        infoCall.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("验证安全密码", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取绑定的银行卡的数据
    public void getCardDatas(final DataListener listener) {
        Call<Object> clone = apiInterface.getCardDatas().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String s = response.body().toString();
                String Cards = s.substring(s.indexOf("cards=[") + 7, s.indexOf(", banks"));
                String Banks = s.substring(s.indexOf("banks=[") + 7, s.indexOf("], provincials"));
                String Privinces = s.substring(s.indexOf("provincials=[") + 13, s.indexOf("], locked"));
                String Locked = s.substring(s.indexOf("locked=") + 7, s.length() - 1);

                Log.d("获取绑定的银行卡的全部数据", response.body().toString());
                Log.d("获取绑定的银行卡的银行数据", Banks);
                String[] split = Banks.split(",");
                List<List<MapsIdAndValue>> CardDatas = new ArrayList<List<MapsIdAndValue>>();
                List<MapsIdAndValue> Mapsbank = new ArrayList<MapsIdAndValue>();
                for (int i = 0; i < split.length; i = i + 2) {
                    Log.d("银行信息==", split[i] + "     " + split[i].length());
                    MapsIdAndValue mapBank = new MapsIdAndValue();

                    String substring = split[i].substring(split[i].indexOf("[") + 1, split[i].length());
                    String substring1 = substring.substring(0, substring.length() - 2);
                    int i1 = Integer.parseInt(substring1);
                    mapBank.setId(i1);
                    Log.d("银行id==", i1 + "");

                    String substring2 = split[i + 1].substring(1, split[i + 1].length() - 1);
                    Log.d("银行Value==", substring2);
                    mapBank.setValues(substring2);

                    Mapsbank.add(mapBank);
                }
                String[] prinvin = Privinces.split(",");
                List<MapsIdAndValue> MapsPrivince = new ArrayList<MapsIdAndValue>();

                for (int i = 0; i < prinvin.length; i = i + 2) {
                    MapsIdAndValue maps = new MapsIdAndValue();
                    String substring = prinvin[i].substring(prinvin[i].indexOf("[") + 1, prinvin[i].length());
                    String substring1 = substring.substring(0, substring.length() - 2);
                    int i1 = Integer.parseInt(substring1);
                    maps.setId(i1);
                    Log.d("省份id==", i1 + "");
                    String substring2 = prinvin[i + 1].substring(1, prinvin[i + 1].length() - 1);
                    Log.d("省份Value==", substring2);
                    maps.setValues(substring2);

                    MapsPrivince.add(maps);
                }
                String[] splitCards = Cards.split(",");
                List<MapsIdAndValue> mapCard = new ArrayList<MapsIdAndValue>();
                for (int i = 0; i < splitCards.length; i = i + 5) {
                    MapsIdAndValue maps = new MapsIdAndValue();
                    String CardId = splitCards[i].substring(splitCards[i].indexOf("[") + 1, splitCards[i].length() - 2);
                    int CardIntId = Integer.parseInt(CardId);
                    maps.setId(CardIntId);
                    String Cardname = splitCards[i + 1].substring(1, splitCards[i + 1].length());
                    maps.setValues(Cardname);
                    String BankName = splitCards[i + 2].substring(1, splitCards[i + 2].length());
                    maps.setBank(BankName);
                    String CardNum = splitCards[i + 3].substring(1, splitCards[i + 3].length());
                    maps.setCardNum(CardNum);
                    String Time = splitCards[i + 4].substring(1, splitCards[i + 4].length());
                    maps.setTime(Time);
                    Log.d("银行卡号信息", maps.toString());
                    mapCard.add(maps);
                    maps.setLocked(Locked);
                }
                CardDatas.add(Mapsbank);
                CardDatas.add(MapsPrivince);
                CardDatas.add(mapCard);
                String values = CardDatas.get(0).get(1).getValues();
                String values1 = CardDatas.get(1).get(1).getValues();
                Log.d("获取银行卡所需的数据返回", values + "    " + values1);
                listener.onReceivedData(API_ID_CARDDATAS, CardDatas, API_ID_ERROR);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //验证银行卡号
    public void getCheckCardNum(DataListener listener, String name, String card) {
        Call<Object> checkBankCardNum = apiInterface.getCheckBankCardNum(name, card);
        Call<Object> clone = checkBankCardNum.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("验证卡号返回的", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //获得省市级联动
    public void getPrivens(final DataListener listener, int id) {
        Call<Object> objectCall = apiInterface.getPrivens(id).clone();
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String string = response.body().toString();
                String citys = string.substring(1, string.length() - 1);
                String[] split = citys.split(",");
                List<MapsIdAndValue> Citys = new ArrayList<MapsIdAndValue>();
                for (int i = 0; i < split.length; i = i + 2) {
                    MapsIdAndValue mapsIdAndValue = new MapsIdAndValue();
                    String cityId = split[i].substring(split[i].indexOf("[") + 1, split[i].length() - 2);
                    int parseInt = Integer.parseInt(cityId);
                    String cityName = split[i + 1].substring(1, split[i + 1].indexOf("]"));
                    mapsIdAndValue.setId(parseInt);
                    mapsIdAndValue.setValues(cityName);
                    Log.d("城市id+name", cityId + "    " + cityName);
                    Citys.add(mapsIdAndValue);
                }
                listener.onReceivedData(API_ID_GETCITYS, Citys, API_ID_ERROR);
                Log.d("获取省市联动", string);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    /*
        bank[int]：开户银行id
        province_id[int]：省份id
        province[string]：省份
        city_id[int]：城市id
        city[string]：城市
        branch[string]：支行名称
        name[string]：持卡人姓名
        card[string]：银行卡号*/
    //保存银行卡  应用场景：绑定新卡保存银行卡数据
    public void getSaveBankCard(final DataListener listener, int bank, int province_id, String province, int city_id, String city, String branch, String name, String card) {
        Call<RestultInfo> saveBankCard = apiInterface.getSaveBankCard(bank, province_id, province, city_id, city, branch, name, card);
        Call<RestultInfo> clone = saveBankCard.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("保存银行卡返回的数据", response.body().toString());
                listener.onReceivedData(API_ID_SAVECARD, response.body(), API_ID_ERROR);

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取提现数据
    public void getWithDrawDatas(DataListener listener) {
        Call<Object> clone = apiInterface.getWithDrawData().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取提现数据", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //添加提现申请
    //  应用场景：提现验证通过后创建提现申请
    public void getWithDrawCreates(DataListener listener, int aid, BigDecimal amount) {
        Call<RestultInfo> clone = apiInterface.getWithDrawCreate(aid, amount).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("提交申请返回的", response.body().toString());

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //修改安全密码
    public void getUpDataSafePwd(DataListener listener, String p0, String p1, String email) {
        Call<RestultInfo> clone = apiInterface.getUpDataSafePwd(p0, p1, email).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("修改安全密码返回的数据", response.body().toString());

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });


    }

    //重置安全密码 或者 登陆密码
    public void getResetPwd(DataListener listener, int type, String p) {
        Call<RestultInfo> clone = apiInterface.getResetPwd(type, p).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("重置安全密码或者登陆密码", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //绑定银行卡锁定
    public void getBingCardLock(final DataListener listener) {
        Call<RestultInfo> clone = apiInterface.getBindingCardLock().clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                listener.onReceivedData(API_ID_CARDLOCK, response.body(), API_ID_ERROR);
                Log.d("绑定银行卡锁定", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取聊天用户
    public void getChatUser(final DataListener listener) {
        Call<Object> clone = apiInterface.getChatUsers().clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String ChatUser = response.body().toString();
                String ChatuserNew = ChatUser.substring(ChatUser.indexOf("[") + 1, ChatUser.length() - 1);
                String[] splitChatUser = ChatuserNew.split(",");
                List<MapsIdAndValue> ChatUsers = new ArrayList<MapsIdAndValue>();
                for (int i = 0; i < splitChatUser.length; i = i + 2) {
                    MapsIdAndValue users = new MapsIdAndValue();
                    int Userid = Integer.parseInt(splitChatUser[i].substring(splitChatUser[i].indexOf("[") + 1, splitChatUser[i].length() - 2));
                    String UserName = splitChatUser[i + 1].substring(1, splitChatUser[i + 1].length() - 1);
                    users.setId(Userid);
                    users.setValues(UserName);
                    ChatUsers.add(users);
                    Log.d("获取单个聊天用户", users.toString());
                }
                listener.onReceivedData(API_ID_CHATUSERS, ChatUsers, API_ID_ERROR);
                Log.d("获取聊天用户", ChatuserNew);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("获取聊天用户的Error", t.toString());
            }
        });
    }

    //获得消息列表
    public void getChatList(DataListener listener, int page, int rows, String sidx, String sord, int send_uid, String from, String to) {
        Call<Object> clone = apiInterface.getChatList(page, rows, sidx, sord, send_uid, from, to).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取消息列表", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //删除聊天消息
    public void getDeleteChatMsg(DataListener listener, int id) {
        Call<RestultInfo> clone = apiInterface.getDeleteChatMsg(id).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("删除聊天消息", response.body().toString());
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //加载聊天信息
    public void getChatMsg(DataListener listener, int id) {
        Call<Object> clone = apiInterface.getChatMsg(id).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("加载聊天信息", response.body().toString());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //发送聊天信息
    public void getSendMsg(DataListener listener, int id, String title, String msg) {
        Call<Object> clone = apiInterface.getSendMsg(id, title, msg).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("发送聊天信息", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //轮询获取新消息
    public void getNewMsg(DataListener listener, int id) {
        Call<Object> clone = apiInterface.getNewMsg(id).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("轮询获取新消息", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}