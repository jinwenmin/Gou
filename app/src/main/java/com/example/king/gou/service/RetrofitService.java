package com.example.king.gou.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.bean.RestultInfo;

import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.bean.ZhuiHaoDetails;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static int API_ID_GAME4 = 18;//获取游戏
    public static int API_ID_GAME5 = 19;//获取游戏
    public static int API_ID_GAME6 = 20;//获取游戏
    public static int API_ID_GAME7 = 21;//获取游戏
    public static int API_ID_TOUZHUSEAR = 22;//获取投注单
    public static int API_ID_ZHUIHAO = 23;//获取追号记录
    public static int API_ID_ZHUIHAODETAILS = 24;//获取追号详情
    public static int API_ID_ZHTZDETAIL = 25;//获取追号投注详情
    private Retrofit retrofit;
    private ApiInterface apiInterface;
    String sessionLoginId;
    String reqkey;

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
        Long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<Object> signout = apiInterface.getSignout(1, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<UserInfo> userInfo = apiInterface.getUserInfo(1, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<Object> activityList = apiInterface.getActivityList(1, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<Object> notices = apiInterface.getNotices(1, reqkey, currentTimeMillis);

        Call<Object> clone = notices.clone();
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
                //Log.d("获取的公告列表==", NoticeContent.get(1)[1] + "  ");
                listener.onRequestEnd(API_ID_NOTICECONTENT);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取公告内容
    public void getNoticesContent(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&id=" + id + "&t=" + currentTimeMillis;
        Call<Object> noticesContent = apiInterface.getNoticesContent(id);
        String s = noticesContent.request().toString();
        Log.d("公告的全体", s);
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
    public void getGame(final DataListener listener, int type, int gid, int tid, int ptid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        map.put("type", type + "");
        map.put("gid", gid + "");
        map.put("tid", tid + "");
        map.put("ptid", ptid + "");

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        final Call<Object> game = apiInterface.getGame(1, type, gid, tid, ptid, reqkey, currentTimeMillis);
        final String s = game.request().toString();
        Log.d("获得游戏的请求体", s);
        Call<Object> clone = game.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                Log.d("获取到的游戏====", response.body().toString());
                String StringType = s.substring(s.indexOf("type=") + 5, s.indexOf("&gid"));
                Log.d("获取到的游戏Type===", StringType + "");
                List<GameType> ListgameTypes = new ArrayList<GameType>();
                String s1 = response.body().toString();
                String GameT = s1.substring(1, s1.length() - 1);
                String[] split = GameT.split(",");
                if ("1".equals(StringType)) {
                    for (int i = 0; i < split.length; i = i + 2) {
                        GameType gameType = new GameType();
                        String GameName = split[i].substring(split[i].indexOf("name=") + 5, split[i].length());
                        String GameTid = split[i + 1].substring(split[i + 1].indexOf("tid=") + 4, split[i + 1].length() - 3);
                        int intGameTid = Integer.parseInt(GameTid);
                        gameType.setName(GameName);
                        gameType.setTid(intGameTid);
                        // Log.d("Game游戏==", gameType.toString());
                        ListgameTypes.add(gameType);
                    }
                }
                if ("2".equals(StringType)) {
                    for (int i = 0; i < split.length; i = i + 3) {
                        //Log.d("Game游戏Split=", split[i]);
                        GameType gameType = new GameType();
                        String GameName = split[i + 1].substring(split[i + 1].indexOf("name=") + 5, split[i + 1].length());
                        String GameTid = split[i + 2].substring(split[i + 2].indexOf("tid=") + 4, split[i + 2].length() - 3);
                        String GameGid = split[i].substring(split[i].indexOf("gid=") + 4, split[i].length() - 2);
                        gameType.setGid(Integer.parseInt(GameGid));
                        gameType.setTid(Integer.parseInt(GameTid));
                        gameType.setName(GameName);
                        //  Log.d("Game游戏==", gameType.toString());
                        ListgameTypes.add(gameType);
                    }
                }
                if ("3".equals(StringType)) {
                    for (int i = 0; i < split.length; i = i + 2) {
                        //Log.d("Game游戏Split=", split[i]);
                        GameType gameType = new GameType();
                        String GameGroupId = split[i].substring(split[i].indexOf("group_id=") + 9, split[i].length() - 2);
                        String GameName = split[i + 1].substring(split[i + 1].indexOf("name=") + 5, split[i + 1].length() - 1);
                        gameType.setGroup_id(Integer.parseInt(GameGroupId));
                        gameType.setName(GameName);
                        //  Log.d("Game游戏==", gameType.toString());
                        ListgameTypes.add(gameType);
                    }
                }
                if ("4".equals(StringType)) {
                    for (int i = 0; i < split.length; i = i + 3) {
                        //Log.d("Game游戏Split=", split[i]);
                        GameType gameType = new GameType();
                        String GameGid = split[i].substring(split[i].indexOf("gid=") + 4, split[i].length() - 2);
                        String GameGroupId = split[i + 1].substring(split[i + 1].indexOf("group_id=") + 9, split[i + 1].length() - 2);
                        String GameName = split[i + 2].substring(split[i + 2].indexOf("name=") + 5, split[i + 2].length() - 1);
                        gameType.setGid(Integer.parseInt(GameGid));
                        gameType.setGroup_id(Integer.parseInt(GameGroupId));
                        gameType.setName(GameName);
                        //Log.d("Game游戏==", gameType.toString());
                        ListgameTypes.add(gameType);
                    }
                    listener.onReceivedData(API_ID_GAME4, ListgameTypes, API_ID_ERROR);
                }
                if ("5".equals(StringType)) {
                    for (int i = 0; i < split.length; i = i + 4) {
                        // Log.d("Game游戏Split=", split[i]);
                        GameType gameType = new GameType();
                        String GameGid = split[i].substring(split[i].indexOf("gid=") + 4, split[i].length() - 2);
                        String GameName = split[i + 1].substring(split[i + 1].indexOf("name=") + 5, split[i + 1].length());
                        String GamePtidId = split[i + 2].substring(split[i + 2].indexOf("ptid=") + 5, split[i + 2].length() - 2);
                        String GameTidId = split[i + 3].substring(split[i + 3].indexOf("tid=") + 4, split[i + 3].length() - 3);
                        gameType.setGid(Integer.parseInt(GameGid));
                        gameType.setName(GameName);
                        gameType.setPtid(Integer.parseInt(GamePtidId));
                        gameType.setTid(Integer.parseInt(GameTidId));
                        ListgameTypes.add(gameType);
                        // Log.d("Game游戏==", gameType.toString());
                    }
                    listener.onReceivedData(API_ID_GAME5, ListgameTypes, API_ID_ERROR);
                }
                if ("7".equals(StringType)) {
                    boolean flag = false;
                    Log.d("Split.leng=", split.length + "");
                    for (int i = 0; i < split.length; i = i + 4) {
                        Log.d("Game游戏Split=", split[i]);
                        GameType gameType = new GameType();
                        if (flag == false) {
                            GameType gameType1 = new GameType();
                            gameType1.setName("全部彩种");
                            gameType1.setTid(0);
                            ListgameTypes.add(gameType1);
                            flag = true;
                        }
                        String GameGid = split[i].substring(split[i].indexOf("gid=") + 4, split[i].length() - 2);
                        String GameGridId = split[i + 1].substring(split[i + 1].indexOf("grid=") + 5, split[i + 1].length() - 2);
                        String GameName = split[i + 2].substring(split[i + 2].indexOf("name=") + 5, split[i + 2].length());
                        String GameTidId = split[i + 3].substring(split[i + 3].indexOf("tid=") + 4, split[i + 3].length() - 3);
                        gameType.setGid(Integer.parseInt(GameGid));
                        gameType.setName(GameName);
                        gameType.setGrid(Integer.parseInt(GameGridId));
                        gameType.setTid(Integer.parseInt(GameTidId));
                        ListgameTypes.add(gameType);
                        Log.d("Game游戏==", gameType.toString());
                    }
                    listener.onReceivedData(API_ID_GAME7, ListgameTypes, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取用户的余额
    public void LoginUserAmount(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        final Call<UserAmount> userAmount = apiInterface.getUserAmount(1, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&u=" + u + "&p=" + p + "&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<Object> backPassword = apiInterface.getBackPassword(1, u, p, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&u=" + u + "&p=" + p + "&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<Object> clone = apiInterface.getBackPasswordSave(1, u, p, reqkey, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        reqkey = "AppClient=1&nickname=" + nickName + "&t=" + currentTimeMillis;
        reqkey = RxUtils.getInstance().md5(reqkey);
        Call<RestultInfo> nickNameChange = apiInterface.getNickNameChange(1, nickName, reqkey, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        String reqkey = "AppClient=1&p0=" + p0 + "&p1=" + p1 + "&p2=" + p2 + "&email=" + email + "&t=" + currentTimeMillis;
        String reqkey2 = RxUtils.getInstance().md5(reqkey);
        Call<RestultInfo> clone = apiInterface.getUpdateFirstPwd(1, p0, p1, p2, email, reqkey2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String reqkey1 = "AppClient=1&t=" + currentTimeMillis;
        String reqkey2 = RxUtils.getInstance().md5(reqkey1);
        Call<Object> clone = apiInterface.getSafeQues(1, reqkey2, currentTimeMillis).clone();
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
                Log.d("首页的公告==", toString);
                String substring = toString.substring(toString.indexOf("content=") + 8, toString.indexOf(", user="));
                listener.onReceivedData(API_ID_HOMENOTICE, substring, API_ID_ERROR);
                Log.d("首页的公告2==", substring);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //修改登录密码
    public void getUpDataPwd(final DataListener listener, String p0, String p1) {
        long currentTimeMillis = System.currentTimeMillis();
        String re1 = "AppClient=1&p0=" + p0 + "&p1=" + p1 + "&t=" + currentTimeMillis;
        String re2 = RxUtils.getInstance().md5(re1);
        Call<RestultInfo> clone = apiInterface.getUpDatePwd(1, p0, p1, re2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&p=" + p + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getCheckSafePwd(1, p, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&q=" + q + "&a=" + a + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> infoCall = apiInterface.getSaveSafeQues(1, q, a, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getCardDatas(1, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&name=" + name + "&card=" + card + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> checkBankCardNum = apiInterface.getCheckBankCardNum(1, name, card, r2, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&id=" + id + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> objectCall = apiInterface.getPrivens(1, id, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&bank=" + bank + "&province_id=" + province_id + "&province=" + province + "&city_id=" + city_id + "&city=" + city + "&branch=" + branch + "&name=" + name + "&card=" + card + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> saveBankCard = apiInterface.getSaveBankCard(1, bank, province_id, province, city_id, city, branch, name, card, r2, currentTimeMillis);
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getWithDrawData(1, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&aid=" + aid + "&amount=" + amount + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getWithDrawCreate(1, aid, amount, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&p0=" + p0 + "&p1=" + p1 + "&email=" + email + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getUpDataSafePwd(1, p0, p1, email, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&type=" + type + "&p=" + p + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getResetPwd(1, type, p, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getBindingCardLock(1, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getChatUsers(1, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&page=" + page + "&rows=" + rows + "&sidx=" + sidx + "&sord=" + sord + "&send_uid=" + send_uid + "&from=" + from + "&to=" + to + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getChatList(1, page, rows, sidx, sord, send_uid, from, to, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&id=" + id + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<RestultInfo> clone = apiInterface.getDeleteChatMsg(1, id, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&id=" + id + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getChatMsg(1, id, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&id=" + id + "&title=" + title + "&msg=" + msg + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getSendMsg(1, id, title, msg, r2, currentTimeMillis).clone();
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
        long currentTimeMillis = System.currentTimeMillis();
        String r1 = "AppClient=1&id=" + id + "&t=" + currentTimeMillis;
        String r2 = RxUtils.getInstance().md5(r1);
        Call<Object> clone = apiInterface.getNewMsg(1, id, r2, currentTimeMillis).clone();
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

    public void getBettingRecord(final DataListener listener, int page, int rows, String sidx, String sord, String from, String to, int id, int rid, int status, String rebuy, String period) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sord", sord);
        map.put("sidx", sidx);
        map.put("from", from);
        map.put("to", to);
        map.put("id", id + "");
        map.put("rid", rid + "");
        map.put("status", status + "");
        map.put("rebuy", rebuy);
        map.put("period", period);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> bettingRecordList = apiInterface.getBettingRecordList(1, page, rows, sidx, sord, from, to, id, rid, status, rebuy, period, reqkey, currentTimeMillis);
        String s = bettingRecordList.request().toString();
        Log.d("查询投注记录请求完全体====", s);
        Call<Object> clone = bettingRecordList.clone();

        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String touZhu = response.body().toString();
                Log.d("查询投注记录====", touZhu);
                String substring = touZhu.substring(touZhu.indexOf("content=[") + 9, touZhu.indexOf("], number"));
                List<TouZhu> ts = new ArrayList<TouZhu>();
                if (substring.length() > 10) {
                    String[] split = substring.split(", ");
                    for (int i = 0; i < split.length; i = i + 11) {
                        TouZhu t = new TouZhu();
                        String bid = split[i].substring(1, split[i].length() - 2);
                        String name = split[i + 1];
                        String rname = split[i + 2];
                        String period = split[i + 3];
                        String picked_text = split[i + 4];
                        String amount = split[i + 5];
                        String prize = split[i + 6];
                        String re_buy = split[i + 7];
                        String buy_time = split[i + 8];
                        String status = split[i + 9].substring(0, split[i + 9].length() - 2);
                        String winning_numbers = split[i + 10].substring(0, split[i + 10].length() - 1);
                        t.setBid(Integer.parseInt(bid));
                        t.setName(name);
                        t.setRname(rname);
                        t.setPeriod(period);
                        t.setPiced_text(picked_text);
                        t.setAmount(Double.parseDouble(amount));
                        t.setPrize(Double.parseDouble(prize));
                        t.setRe_buy(re_buy);
                        t.setBuy_time(buy_time);
                        t.setStatus(Integer.parseInt(status));
                        t.setWinning_numbers(winning_numbers);
                        ts.add(t);
                        Log.d("单个的投注记录", split[i]);
                    }
                    listener.onReceivedData(API_ID_TOUZHUSEAR, ts, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("查询投注记录错误信息", t.toString());
            }
        });

    }

    public void getKeepNum(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int id, int rid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("from", from + "");
        map.put("to", to + "");
        map.put("id", id + "");
        map.put("rid", rid + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> keepNum = apiInterface.getKeepNum(1, page, rows, sidx, sord, from, to, id, rid, reqkey, currentTimeMillis);
        Call<Object> clone = keepNum.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String toString = response.body().toString();
                String substring = toString.substring(toString.indexOf("content=[") + 9, toString.indexOf("], number"));
                Log.d("追号记录SubString=", substring);
                if (substring.length() > 10) {
                    String[] sp = substring.split(", ");
                    List<ZhuiHao> zhuiHaos = new ArrayList<ZhuiHao>();
                    for (int i = 0; i < sp.length; i = i + 17) {
                        Log.d("追号记录截取1", sp[i]);
                        ZhuiHao z = new ZhuiHao();
                        String id = sp[i].substring(1, sp[i].length() - 2);
                        String number = sp[i + 1];
                        String purchase_date = sp[i + 2];
                        String gname = sp[i + 3];
                        String rname = sp[i + 4];
                        String start_period = sp[i + 5];
                        String periods = sp[i + 6].substring(0, sp[i + 6].length() - 2);
                        String purchase_periods = sp[i + 7].substring(0, sp[i + 7].length() - 2);
                        String picked_numbers = sp[i + 8];
                        String mode = sp[i + 9].substring(0, sp[i + 9].length() - 2);
                        String amount = sp[i + 10];
                        String purchase_amount = sp[i + 11];
                        String cancel_amount = sp[i + 12];
                        String prize_num = sp[i + 13].substring(0, sp[i + 13].length() - 2);
                        String prize_amount = sp[i + 14];
                        Log.d("i+15", sp[i + 15]);
                        String status = sp[i + 16].substring(0, sp[i + 16].length() - 3);
                        z.setId(Integer.parseInt(id));
                        z.setNumber(number);
                        z.setPurchase_date(purchase_date);
                        z.setGname(gname);
                        z.setRname(rname);
                        z.setStart_period(start_period);
                        z.setPeriods(Integer.parseInt(periods));
                        z.setPurchase_periods(Integer.parseInt(purchase_periods));
                        z.setPicked_numbers(picked_numbers);
                        z.setMode(Integer.parseInt(mode));
                        z.setAmount(Double.parseDouble(amount));
                        z.setPurchase_amount(Double.parseDouble(purchase_amount));
                        z.setCancel_amount(Double.parseDouble(cancel_amount));
                        z.setPrize_num(Integer.parseInt(prize_num));
                        z.setPrize_amount(Double.parseDouble(prize_amount));
                        z.setStatus(Integer.parseInt(status));
                        Log.d("查询追号的Bean", z.toString());
                        zhuiHaos.add(z);
                    }
                    listener.onReceivedData(API_ID_ZHUIHAO, zhuiHaos, API_ID_ERROR);
                    Log.d("查询追号返回数据", toString);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("查询追号返回数据异常", t.toString());
            }
        });
    }

    //查询追号详情
    public void getKeepNumDeTails(final DataListener listener, int id) {
        Call<Object> keepNumDetails = apiInterface.getKeepNumDetails(id);
        Call<Object> clone = keepNumDetails.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String s = response.body().toString();
                s = s.substring(s.indexOf("others={") + 8, s.length() - 2);
                Log.d("查询追号详情===", s);
                ZhuiHaoDetails zz = new ZhuiHaoDetails();
                String id = s.substring(s.indexOf("id=") + 3, s.indexOf(", amount"));
                id = id.substring(0, id.length() - 2);
                String amount = s.substring(s.indexOf("amount=") + 7, s.indexOf(", cancelAmount"));
                String cancelAmount = s.substring(s.indexOf("cancelAmount=") + 13, s.indexOf(", cancelPeriods"));
                String cancelPeriods = s.substring(s.indexOf("cancelPeriods=") + 14, s.indexOf(", drawPeriod"));
                cancelPeriods = cancelPeriods.substring(0, cancelPeriods.length() - 2);
                String drawPeriod = s.substring(s.indexOf("drawPeriod=") + 11, s.indexOf(", gid"));
                String gid = s.substring(s.indexOf("gid=") + 4, s.indexOf(", mode"));
                gid = gid.substring(0, gid.length() - 2);
                String mode = s.substring(s.indexOf("mode=") + 5, s.indexOf(", number"));
                mode = mode.substring(0, mode.length() - 2);
                String number = s.substring(s.indexOf("number=") + 7, s.indexOf(", periods"));
                String periods = s.substring(s.indexOf("periods=") + 8, s.indexOf(", pickedNumbers"));
                periods = periods.substring(0, periods.length() - 2);
                String pickedNumbers = s.substring(s.indexOf("pickedNumbers=") + 14, s.indexOf(", purchaseAmount"));
                String purchaseAmount = s.substring(s.indexOf("purchaseAmount=") + 15, s.indexOf(", purchaseDate"));
                String purchaseDate = s.substring(s.indexOf("purchaseDate=") + 13, s.indexOf(", purchasePeriods"));
                String purchasePeriods = s.substring(s.indexOf("purchasePeriods=") + 16, s.indexOf(", rulesId"));
                purchasePeriods = purchasePeriods.substring(0, purchasePeriods.length() - 2);
                String rulesId = s.substring(s.indexOf("rulesId=") + 8, s.indexOf(", startPeriod"));
                rulesId = rulesId.substring(0, rulesId.length() - 2);
                String startPeriod = s.substring(s.indexOf("startPeriod=") + 12, s.indexOf(", bids"));
                String bids = s.substring(s.indexOf("bids=") + 5, s.indexOf(", status"));
                String status = s.substring(s.indexOf("status=") + 7, s.indexOf(", stopByWin"));
                status = status.substring(0, status.length() - 2);
                String stopByWin = s.substring(s.indexOf("stopByWin=") + 10, s.indexOf(", uid"));
                stopByWin = stopByWin.substring(0, stopByWin.length() - 2);
                String uid = s.substring(s.indexOf("uid=") + 4, s.length() - 2);
                zz.setId(Integer.parseInt(id));
                zz.setAmount(Double.parseDouble(amount));
                zz.setCancelAmount(Double.parseDouble(cancelAmount));
                zz.setCancelPeriods(Integer.parseInt(cancelPeriods));
                zz.setDrawPeriod(drawPeriod);
                zz.setGid(Integer.parseInt(gid));
                zz.setMode(Integer.parseInt(mode));
                zz.setNumber(number);
                zz.setPeriods(Integer.parseInt(periods));
                zz.setPickedNumbers(pickedNumbers);
                zz.setPurchaseAmount(Double.parseDouble(purchaseAmount));
                zz.setPurchaseDate(purchaseDate);
                zz.setPurchasePeriods(Integer.parseInt(purchasePeriods));
                zz.setRulesId(Integer.parseInt(rulesId));
                zz.setStartPeriod(startPeriod);
                zz.setBids(bids);
                zz.setStatus(Integer.parseInt(status));
                zz.setStopByWin(Integer.parseInt(stopByWin));
                zz.setUid(Integer.parseInt(uid));
                Log.d("查询追号详情===.toString", zz.toString());
                listener.onReceivedData(API_ID_ZHUIHAODETAILS, zz, API_ID_ERROR);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //查询追号投注记录
    public void getKeepNumBet(final DataListener listener, int page, int rows, String sidx, String sord, int id) {
        long l = System.currentTimeMillis();
        Map map = new HashMap();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx);
        map.put("sord", sord);
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, l);
        Call<Object> keepNumBet = apiInterface.getKeepNumBet(1, page, rows, sidx, sord, id, reqkey, l);
        Call<Object> clone = keepNumBet.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                String s = response.body().toString();
                Log.d("查询追号投注记录", s);
                String substring = s.substring(s.indexOf("content=[") + 9, s.indexOf("], number="));
                String[] sp = substring.split(", ");
                if (sp.length > 4) {
                    List<ZhuiHao> zhs = new ArrayList<ZhuiHao>();

                    for (int i = 0; i < sp.length; i = i + 5) {
                        Log.d("查询追号投注记录Split", sp[i]);
                        String id = sp[i].substring(sp[i].indexOf("[") + 1, sp[i].length() - 2);//投注单id
                        String draw_period = sp[i + 1];//投注期号
                        String multiple = sp[i + 2].substring(0, sp[i + 2].length() - 2);//投注倍数
                        String status = sp[i + 3].substring(0, sp[i + 3].length() - 2);//投注单状态
                        ZhuiHao zh = new ZhuiHao();
                        zh.setId(Integer.parseInt(id));
                        zh.setNumber(draw_period);
                        zh.setPrize_num(Integer.parseInt(multiple));
                        zh.setStatus(Integer.parseInt(status));
                        Log.d("查询追号投注记录Bean", zh.toString());
                        zhs.add(zh);
                    }
                    listener.onReceivedData(API_ID_ZHTZDETAIL, zhs, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}