package com.example.king.gou.service;

import android.accounts.Account;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.media.Image;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import com.example.king.gou.MyApp;
import com.example.king.gou.bean.AccountChange;
import com.example.king.gou.bean.ActivityBean;
import com.example.king.gou.bean.AliPay;
import com.example.king.gou.bean.AliPayBean;
import com.example.king.gou.bean.BettingDetail;
import com.example.king.gou.bean.BettingSync;
import com.example.king.gou.bean.CardsData;
import com.example.king.gou.bean.CunQu;
import com.example.king.gou.bean.EBao;
import com.example.king.gou.bean.GamePrize;
import com.example.king.gou.bean.GameType;
import com.example.king.gou.bean.JoinActivity;
import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.LotteryLoss;
import com.example.king.gou.bean.MapsIdAndValue;
import com.example.king.gou.bean.Message;
import com.example.king.gou.bean.RecordList;
import com.example.king.gou.bean.Remittance;
import com.example.king.gou.bean.Remittance_Record;
import com.example.king.gou.bean.RestultInfo;

import com.example.king.gou.bean.SetRate;
import com.example.king.gou.bean.ShareData;
import com.example.king.gou.bean.SreCharge;
import com.example.king.gou.bean.SwitchG;
import com.example.king.gou.bean.SwitchGame;
import com.example.king.gou.bean.TeamUserInfo;
import com.example.king.gou.bean.TouZhu;
import com.example.king.gou.bean.UserActivity;
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;
import com.example.king.gou.bean.UserTeamBetting;
import com.example.king.gou.bean.VIPAccountChange;
import com.example.king.gou.bean.WithDraw;
import com.example.king.gou.bean.ZhuiHao;
import com.example.king.gou.bean.ZhuiHaoCNum;
import com.example.king.gou.bean.ZhuiHaoDetails;
import com.example.king.gou.ui.MainActivity;
import com.example.king.gou.utils.AddCookiesInterceptor;
import com.example.king.gou.utils.ApiInterface;
import com.example.king.gou.utils.HttpEngine;
import com.example.king.gou.utils.ReceivedCookiesInterceptor;
import com.example.king.gou.utils.RxUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.okhttp.ResponseBody;
import com.zhy.autolayout.utils.L;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import it.sephiroth.android.library.picasso.Picasso;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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
    public static int API_ID_NOTICECONTENT2 = 6;//公告详情G
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
    public static int API_ID_CACEL = 26;//撤销投注单
    public static int API_ID_ACCOUNTCHANGE = 27;//个人账变记录
    public static int API_ID_RECHARGEDRAW = 28;//个人充提记录
    public static int API_ID_PROFITLOSS = 29;//彩票盈亏
    public static int API_ID_IMAGECHECK = 30;//获取验证图片信息
    public static int API_ID_TEAMCQ = 31;//团队充提记录
    public static int API_ID_TEAMACCOUNTCHANGE = 32;//团队账变记录
    public static int API_ID_IMAGECHECKS = 33;//验证码校验
    public static int API_ID_SIGNUP = 34;//验证码校验
    public static int API_ID_BETTINGSYNC = 35;//倒计时同步
    public static int API_ID_TOKENLOGIN = 36;//TOKEN登陆
    public static int API_ID_TEAMBETTING = 37;//团队报表彩票投注
    public static int API_ID_TEAMUSERINFO = 38;//会员管理
    public static int API_ID_ADDVIPCODE = 39;//推广码
    public static int API_ID_ADDCIPSAVE = 40;//添加会员保存
    public static int API_ID_USERSTA = 41;//会员统计
    public static int API_ID_TEAMUSERLIST = 42;//会员统计2
    public static int API_ID_TEAMBALANCEVIEW = 43;//查询会员余额
    public static int API_ID_UREBATEDATA = 44;//查询会员返点
    public static int API_ID_SETRATESAVE = 45;//查询会员返点
    public static int API_ID_SRECHARGE = 46;//获取上级充值数据
    public static int API_ID_SRECHARGE2 = 47;//获取上级充值数据2
    public static int API_ID_SRECHARGE3 = 471;//获取上级充值数据3
    public static int API_ID_OWNTRANSFER = 48;//给下级充值
    public static int API_ID_ACTIVITYLIST = 49;//获取活动列表
    public static int API_ID_ACTIVITYDETAIL = 50;//获取活动详情
    public static int API_ID_GAMEPRIZE = 51;//游戏奖金
    public static int API_ID_RECORDLIST = 52;//开奖记录
    public static int API_ID_JOINACTIVITY = 53;//开奖记录
    public static int API_ID_DRAWMONEY = 54;//领取活动奖金
    public static int API_ID_WITHDRAW = 55;//获取提现数据
    public static int API_ID_WITHDRAWCREATE = 56;//提交提现申请
    public static int API_ID_DAILYRECHARGE = 57;//日工资充值
    public static int API_ID_SAFEPWDCHECK = 58;//验证安全问题
    public static int API_ID_RESETPWD = 59;//重置密码
    public static int API_ID_UPDATASAFEPWD = 60;//修改安全密码
    public static int API_ID_LOTTERYLOSS = 61;//团队盈亏
    public static int API_ID_HISTORYGAME = 62;//历史投注的开奖记录
    public static int API_ID_MYACTIVITYLIST = 63;//个人活动记录
    public static int API_ID_TEAMACTIVITYLIST = 64;//团队活动记录
    public static int API_ID_VIPACCCHANGE = 65;//会员帐变记录
    public static int API_ID_SHAREDATA = 66;//推广设置
    public static int API_ID_BETTINGWINNUM = 67;//同步上期开奖数据
    public static int API_ID_CHATLIST = 68;//获取聊天消息
    public static int API_ID_SENDMSG = 70;//获取聊天消息
    public static int API_ID_GETNEWMSG = 71;//轮询消息
    public static int API_ID_SWITCHGAME = 72;//获取游戏玩法详情
    public static int API_ID_SWITCHGAME_STATUS = 721;//获取游戏玩法状态
    public static int API_ID_BEETING_AUTO = 73;//获取追号信息
    public static int API_ID_USERBETTING = 74;//个人报表彩票投注
    public static int API_ID_SENDBETTING = 75;//提交购彩单
    public static int API_ID_PWDPROTACT = 76;//密码保护 密码
    public static int API_ID_BETTINGDETAIL = 77;//投注单详情
    public static int API_ID_BETREVOKE1 = 78;//撤销投注单
    public static int API_ID_GENERALIZESAVE = 79;//推广设置
    public static int API_ID_CHECKCAEDNUM = 80;//验证银行卡
    public static int API_ID_YEEPAY = 81;//APP易宝支付初始参数获取
    public static int API_ID_OWNRECHARGESAVE = 82;//APP易宝支付初始参数获取
    public static int API_ID_ALIPAY = 83;//APP易宝支付初始参数获取
    public static int API_ID_ALIPAYRECHARGESAVE = 84;//APP易宝支付初始参数获取
    public static int API_ID_ALIPAYSUBMIT = 85;//支付宝支付确认
    public static int API_ID_REMITTANCE = 86;//
    public static int API_ID_RECEIVE = 87;//领取活动奖金

    private Retrofit retrofit;
    private ApiInterface apiInterface;
    String sessionLoginId;
    String reqkey;
    UserInfo users = null;

    public static class SingleInstanceHolder {
        private static RetrofitService retrofitService = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return SingleInstanceHolder.retrofitService;

    }

    public UserInfo getUser() {
        if (users == null) {
            return null;
        } else {
            return users;
        }
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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new ReceivedCookiesInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS).build();
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
    public void Signout() {
        Long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> signout = apiInterface.getSignout(/*1, reqkey, currentTimeMillis*/);
        Call<Object> clone = signout.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {

                    Log.d("登出的没日志==", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    public void LogOut() {
        Long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> signout = apiInterface.getLogout(/*1, reqkey, currentTimeMillis*/);
        Call<Object> clone = signout.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {

                    Log.d("登出的日志==", response.body().toString());
                }
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

        Call<Map<String, Object>> loginState = apiInterface.getLoginState(luid, uonline, type, ids, gets);
        System.out.println("登录信息=" + luid + " " + uonline + " " + type + " " + ids + " " + gets);
        Call<Map<String, Object>> clone = loginState.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {

                    //获取cookie
                    String sessionId = getSessionCookie(response.headers().get("Set-Cookie"));
                    System.out.println("Cookie登录状态==" + sessionId);
                    System.out.println("用户信息登录状态=" + response.body());
                    Map<String, Object> map = response.body();
                    String uname = null;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("uname".equals(entry.getKey())) {
                                uname = (String) entry.getValue();
                            }
                        }
                    }
                /*    LoginState loginS = new LoginState();
                    loginS.setSessionId(sessionId);*/
                    listener.onReceivedData(API_ID_LOGINSTATE, uname, API_ID_ERROR);

                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                System.out.println("登录状态错误信息==" + t.toString());
                listener.onReceivedData(API_ID_LOGINSTATE, null, API_ID_ERROR);
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
                System.out.println("用户信息Code==" + response.code());
                if (response.code() == 200) {
                    listener.onRequestStart(API_ID_LOGIN);
                    //获取cookie
                    sessionLoginId = getSessionCookie(response.headers().get("Set-Cookie"));
                    System.out.println("Cookie==" + sessionLoginId);
                    Gson gson = new GsonBuilder().setLenient().create();
                    Login body = response.body();
                    body.setSessionId(sessionLoginId);
                    System.out.println("用户信息==" + body);
                    listener.onReceivedData(API_ID_LOGIN, body, -1);
                    listener.onRequestEnd(API_ID_LOGIN);
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("用户信息Error", "");
            }
        });
    }


    //用户的基本信息
    public void GetUserInfo(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();

        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<UserInfo> userInfo = apiInterface.getUserInfo(1, reqkey, currentTimeMillis);
        Call<UserInfo> clone = userInfo.clone();
        clone.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, retrofit2.Response<UserInfo> response) {
                if (response.code() == 200) {
                    listener.onRequestStart(API_ID_USERINFO);
                    List<UserInfo> userInfos = new ArrayList<UserInfo>();
                    userInfos.add(0, response.body());
                    listener.onReceivedData(API_ID_USERINFO, userInfos, API_ID_ERROR);
                    System.out.println("用户的基本信息==" + response.body());
                    users = response.body();
                    listener.onRequestEnd(API_ID_USERINFO);
                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    //获取活动列表
    public void GetActivityList(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<List<Object>>> activityList = apiInterface.getActivityList(1, reqkey, currentTimeMillis);
        Call<List<List<Object>>> clone = activityList.clone();
        clone.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, retrofit2.Response<List<List<Object>>> response) {
                if (response.code() == 200) {
                    List<UserActivity> uac = new ArrayList<UserActivity>();
                    String s = response.body().toString();
                    Log.d("获取活动列表", s + "");
                    List<List<Object>> list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        List<Object> l = list.get(i);
                        if (l != null) {
                            double id = (double) l.get(0);
                            String name = (String) l.get(1);
                            UserActivity uc = new UserActivity();
                            uc.setAid(RxUtils.getInstance().getInt(id));
                            uc.setName(name);
                            uac.add(uc);
                        }

                    }
                    listener.onReceivedData(API_ID_ACTIVITYLIST, uac, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {

            }
        });
    }

    //获取活动详情
    public void getActivityNoticesView(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> activityNoticesView = apiInterface.getActivityNoticesView(1, id, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = activityNoticesView.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取活动详情", s);
                    Map<String, Object> map = response.body();
                    boolean rc = false;
                    String msg = null;
                    double others = 0;
                    double id = 0;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("msg".equals(entry.getKey())) {
                                msg = (String) entry.getValue();
                            }
                            if ("others".equals(entry.getKey())) {
                                others = (double) entry.getValue();
                            }
                            if ("id".equals(entry.getKey())) {
                                id = (double) entry.getValue();
                            }
                        }
                    }

                    if (rc) {
                        UserActivity uc = new UserActivity();
                        uc.setMsg(msg);
                        uc.setOthers(RxUtils.getInstance().getInt(others));
                        uc.setId(RxUtils.getInstance().getInt(id));
                        Log.d("ActivityDetail", uc.toString());
                        listener.onReceivedData(API_ID_ACTIVITYDETAIL, uc, API_ID_ERROR);
                    }

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //报名参加活动
    public void getActivityUserApply(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> activityUserApply = apiInterface.getActivityUserApply(1, id, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = activityUserApply.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("报名参加活动", s);
                    Map<String, Object> map = response.body();
                    boolean rc = false;
                    String msg = null;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("msg".equals(entry.getKey())) {
                                msg = (String) entry.getValue();
                            }

                        }
                    }
                    JoinActivity joinActivity = new JoinActivity();
                    joinActivity.setRc(rc);
                    joinActivity.setMsg(msg);
                    listener.onReceivedData(API_ID_JOINACTIVITY, joinActivity, API_ID_ERROR);


                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //获取公告
    public void GetNotices(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<List<Object>>> notices = apiInterface.getNotices(1, reqkey, currentTimeMillis);

        Call<List<List<Object>>> clone = notices.clone();
        listener.onRequestStart(API_ID_NOTICECONTENT);
        clone.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, retrofit2.Response<List<List<Object>>> response) {
                if (response.code() == 200) {
                    Log.d("获取的公告列表==", response.body() + "  ");
                    List<List<Object>> No = new ArrayList<>();
                    No = response.body();
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

            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {

            }
        });


    }

    //获取公告内容
    public void getNoticesContent(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> noticesContent = apiInterface.getNoticesContent(id, 1, reqkey, currentTimeMillis);
        String s = noticesContent.request().toString();
        Log.d("公告的全体", s);
        Call<Map<String, Object>> clone = noticesContent.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("一个公告的内容Code==", response.code() + "");
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("一个公告的内容1===", s + "");
                    Map<String, Object> map = response.body();
                    Map<String, Object> oth = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("others".equals(entry.getKey())) {
                                oth = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    String time = null;
                    String user = null;
                    String content = null;
                    if (oth.size() > 0) {
                        for (Map.Entry<String, Object> entry : oth.entrySet()) {
                            if ("time".equals(entry.getKey())) {
                                time = (String) entry.getValue();
                            }
                            if ("user".equals(entry.getKey())) {
                                user = (String) entry.getValue();
                            }
                            if ("content".equals(entry.getKey())) {
                                content = (String) entry.getValue();
                            }
                        }
                    }
                    String[] strings = new String[]{content, time, user};
                    Log.d("Content的内容===", content);

                    listener.onReceivedData(API_ID_NOTICECONTENT2, strings, API_ID_ERROR);

                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //获取玩法
    public void getGametype(DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> clone = apiInterface.getGameType(id, 1, reqkey, currentTimeMillis).clone();
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
        final Call<List<Map<String, Object>>> game = apiInterface.getGame(1, type, gid, tid, ptid, reqkey, currentTimeMillis);
        final String s = game.request().toString();
        Log.d("获得游戏的请求体", s);
        Call<List<Map<String, Object>>> clone = game.clone();
        clone.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, retrofit2.Response<List<Map<String, Object>>> response) {
                if (response.code() == 200) {
                    Log.d("获取到的游戏====", response.body().toString());
                    String StringType = s.substring(s.indexOf("type=") + 5, s.indexOf("&gid"));
                    Log.d("获取到的游戏Type===", StringType + "");
                    List<GameType> ListgameTypes = new ArrayList<GameType>();
                    String s1 = response.body().toString();
                    String GameT = s1.substring(1, s1.length() - 1);
                    String[] split = GameT.split(",");

                    if ("4".equals(StringType)) {
                        GameType g2 = new GameType();
                        g2.setGrid(0);
                        g2.setName("全部游戏");
                        ListgameTypes.add(g2);
                        List<Map<String, Object>> lis = response.body();
                        for (int i = 0; i < lis.size(); i++) {
                            Map<String, Object> map = lis.get(i);
                            double gid = 0;
                            double group_id = 0;
                            String name = null;
                            if (map.size() > 0) {
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if ("gid".equals(entry.getKey())) {
                                        gid = (double) entry.getValue();
                                    }
                                    if ("group_id".equals(entry.getKey())) {
                                        group_id = (double) entry.getValue();
                                    }
                                    if ("name".equals(entry.getKey())) {
                                        name = (String) entry.getValue();
                                    }
                                }
                            }
                            GameType gameType = new GameType();
                            gameType.setGid(RxUtils.getInstance().getInt(gid));
                            gameType.setGroup_id(RxUtils.getInstance().getInt(group_id));
                            gameType.setName(name);
                            Log.d("Game游戏4==", gameType.toString());
                            ListgameTypes.add(gameType);
                        }

                        listener.onReceivedData(API_ID_GAME4, ListgameTypes, API_ID_ERROR);
                    }

                    if ("7".equals(StringType)) {
                        boolean flag = false;
                        Log.d("Split.leng=", split.length + "");

                        if (flag == false) {
                            GameType gameType1 = new GameType();
                            gameType1.setName("全部玩法");
                            gameType1.setTid(0);
                            ListgameTypes.add(gameType1);
                            flag = true;
                        }
                        List<Map<String, Object>> list7 = response.body();
                        for (int i = 0; i < list7.size(); i++) {
                            Map<String, Object> map7 = list7.get(i);
                            String classCode = null;
                            double gid = 0;
                            double grid = 0;
                            double tid = 0;
                            String name = null;
                            if (map7.size() > 0) {
                                for (Map.Entry<String, Object> entry : map7.entrySet()) {
                                    if ("gid".equals(entry.getKey())) {
                                        gid = (double) entry.getValue();
                                    }
                                    if ("classCode".equals(entry.getKey())) {
                                        classCode = (String) entry.getValue();
                                    }
                                    if ("name".equals(entry.getKey())) {
                                        name = (String) entry.getValue();
                                    }
                                    if ("tid".equals(entry.getKey())) {
                                        tid = (double) entry.getValue();
                                    }
                                    if ("grid".equals(entry.getKey())) {
                                        grid = (double) entry.getValue();
                                    }
                                }
                            }
                            GameType gameType = new GameType();
                            gameType.setGid(RxUtils.getInstance().getInt(gid));

                            gameType.setGrid(RxUtils.getInstance().getInt(grid));
                            int tidd = RxUtils.getInstance().getInt(tid);
                            gameType.setTid(tidd);
                            String ns = "";
                            if (tidd == 2 || tidd == 3 || tidd == 4 || tidd == 62 || tidd == 63 || tidd == 64 || tidd == 93 || tidd == 94 || tidd == 95 || tidd == 527 || tidd == 528 || tidd == 529
                                    || tidd == 343 || tidd == 344 || tidd == 345 || tidd == 237 || tidd == 238 || tidd == 239 || tidd == 381 || tidd == 382 || tidd == 383
                                    || tidd == 605 || tidd == 606 || tidd == 607 || tidd == 643 || tidd == 644 || tidd == 645 || tidd == 699 || tidd == 700 || tidd == 701
                                    ) {
                                ns = "五星";
                            }
                            if (tidd == 6 || tidd == 7 || tidd == 66 || tidd == 67 || tidd == 97 || tidd == 98 || tidd == 531 || tidd == 532
                                    || tidd == 347 || tidd == 348 || tidd == 241 || tidd == 242 || tidd == 385 || tidd == 386
                                    || tidd == 609 || tidd == 610 || tidd == 647 || tidd == 648 || tidd == 703 || tidd == 704
                                    ) {
                                ns = "四星";
                            }
                            if (tidd == 9 || tidd == 10 || tidd == 69 || tidd == 70 || tidd == 100 || tidd == 101 || tidd == 534 || tidd == 535
                                    || tidd == 350 || tidd == 351 || tidd == 244 || tidd == 245 || tidd == 388 || tidd == 389
                                    || tidd == 612 || tidd == 613 || tidd == 650 || tidd == 651 || tidd == 706 || tidd == 707
                                    ) {
                                ns = "前三";
                            }
                            if (tidd == 12 || tidd == 13 || tidd == 72 || tidd == 73 || tidd == 103 || tidd == 104 || tidd == 537 || tidd == 538
                                    || tidd == 353 || tidd == 354 || tidd == 247 || tidd == 248 || tidd == 391 || tidd == 392
                                    || tidd == 615 || tidd == 616 || tidd == 653 || tidd == 654 || tidd == 709 || tidd == 710
                                    ) {
                                ns = "后三";
                            }
                            if (tidd == 263 || tidd == 264 || tidd == 266 || tidd == 267 || tidd == 269 || tidd == 270 || tidd == 553 || tidd == 554
                                    || tidd == 369 || tidd == 370 || tidd == 278 || tidd == 279 || tidd == 407 || tidd == 408
                                    || tidd == 631 || tidd == 632 || tidd == 669 || tidd == 670 || tidd == 725 || tidd == 726
                                    ) {
                                ns = "中三";
                            }

                            if (tidd == 15 || tidd == 16 || tidd == 75 || tidd == 76 || tidd == 106 || tidd == 107 || tidd == 540 || tidd == 541
                                    || tidd == 356 || tidd == 357 || tidd == 250 || tidd == 251 || tidd == 394 || tidd == 395
                                    || tidd == 618 || tidd == 619 || tidd == 656 || tidd == 657 || tidd == 712 || tidd == 713
                                    ) {
                                ns = "前二";
                            }
                            if (tidd == 18 || tidd == 19 || tidd == 78 || tidd == 79 || tidd == 109 || tidd == 110 || tidd == 543 || tidd == 544
                                    || tidd == 359 || tidd == 360 || tidd == 253 || tidd == 254 || tidd == 397 || tidd == 398
                                    || tidd == 621 || tidd == 622 || tidd == 659 || tidd == 660 || tidd == 715 || tidd == 716
                                    ) {
                                ns = "后二";
                            }
                            if (tidd == 20 || tidd == 80 || tidd == 111 || tidd == 545 || tidd == 361 || tidd == 255 || tidd == 399 || tidd == 623 || tidd == 661 || tidd == 717) {
                                ns = "定位胆";
                            }
                            if (tidd == 21 || tidd == 81 || tidd == 112 || tidd == 546 || tidd == 362 || tidd == 256 || tidd == 400 || tidd == 624 || tidd == 662 || tidd == 718) {
                                ns = "不定位";
                            }
                            if (tidd == 48 || tidd == 49 || tidd == 50 || tidd == 82 || tidd == 83 || tidd == 84 || tidd == 113 || tidd == 114 || tidd == 115 || tidd == 547 || tidd == 548 || tidd == 549
                                    || tidd == 363 || tidd == 364 || tidd == 365 || tidd == 257 || tidd == 258 || tidd == 259 || tidd == 401 || tidd == 402 || tidd == 403
                                    || tidd == 625 || tidd == 626 || tidd == 627 || tidd == 663 || tidd == 664 || tidd == 665 || tidd == 719 || tidd == 720 || tidd == 721
                                    ) {
                                ns = "不定位";
                            }
                            if (tidd == 749 || tidd == 751 || tidd == 753 || tidd == 769 || tidd == 761 || tidd == 759 || tidd == 763 || tidd == 773 || tidd == 775 || tidd == 777) {
                                ns = "龙虎斗";
                            }
                            if (tidd == 456 || tidd == 457 || tidd == 60 || tidd == 86 || tidd == 508 || tidd == 509 || tidd == 117 || tidd == 510 || tidd == 511 || tidd == 551 || tidd == 564 || tidd == 565
                                    || tidd == 367 || tidd == 512 || tidd == 513 || tidd == 261 || tidd == 458 || tidd == 459 || tidd == 405 || tidd == 514 || tidd == 515
                                    || tidd == 629 || tidd == 686 || tidd == 687 || tidd == 667 || tidd == 688 || tidd == 689 || tidd == 723 || tidd == 736 || tidd == 737
                                    ) {
                                ns = "趣味";
                            }
                            if (tidd == 287 || tidd == 288 || tidd == 296 || tidd == 297 || tidd == 306 || tidd == 305 || tidd == 562 || tidd == 563
                                    || tidd == 378 || tidd == 379 || tidd == 332 || tidd == 333 || tidd == 416 || tidd == 417
                                    || tidd == 640 || tidd == 641 || tidd == 678 || tidd == 679 || tidd == 734 || tidd == 735
                                    ) {
                                ns = "任选四";
                            }
                            if (tidd == 284 || tidd == 285 || tidd == 293 || tidd == 302 || tidd == 303 || tidd == 294 || tidd == 559 || tidd == 560
                                    || tidd == 375 || tidd == 376 || tidd == 329 || tidd == 330 || tidd == 413 || tidd == 414
                                    || tidd == 637 || tidd == 638 || tidd == 675 || tidd == 676 || tidd == 731 || tidd == 732
                                    ) {
                                ns = "任选三";
                            }
                            if (tidd == 281 || tidd == 282 || tidd == 291 || tidd == 290 || tidd == 299 || tidd == 300 || tidd == 556 || tidd == 557
                                    || tidd == 372 || tidd == 373 || tidd == 326 || tidd == 327 || tidd == 410 || tidd == 411
                                    || tidd == 634 || tidd == 635 || tidd == 672 || tidd == 673 || tidd == 728 || tidd == 729
                                    ) {
                                ns = "任选二";
                            }
                            if (tidd == 22 || tidd == 215 || tidd == 334 || tidd == 690) {
                                ns = "三码";
                            }
                            if (tidd == 23 || tidd == 216 || tidd == 335 || tidd == 691) {
                                ns = "二码";
                            }
                            if (tidd == 24 || tidd == 217 || tidd == 336 || tidd == 692) {
                                ns = "不定位";
                            }
                            if (tidd == 25 || tidd == 218 || tidd == 337 || tidd == 693) {
                                ns = "定位胆";
                            }
                            if (tidd == 26 || tidd == 219 || tidd == 338 || tidd == 694) {
                                ns = "趣味性";
                            }
                            if (tidd == 27 || tidd == 220 || tidd == 339 || tidd == 695) {
                                ns = "任选复式";
                            }
                            if (tidd == 28 || tidd == 221 || tidd == 340 || tidd == 696) {
                                ns = "任选单式";
                            }
                            if (tidd == 40) {
                                ns = "和值";
                            }
                            if (tidd == 41) {
                                ns = "三同号通选";
                            }
                            if (tidd == 42) {
                                ns = "三同号单选";
                            }
                            if (tidd == 43) {
                                ns = "二同号复选";
                            }
                            if (tidd == 44) {
                                ns = "二同号单选";
                            }
                            if (tidd == 45) {
                                ns = "三不同号";
                            }
                            if (tidd == 46) {
                                ns = "二不同号";
                            }
                            if (tidd == 47) {
                                ns = "三连号通选";
                            }
                            if (tidd == 230) {
                                ns = "猜冠军";
                            }
                            if (tidd == 231) {
                                ns = "猜冠亚军";
                            }
                            if (tidd == 232) {
                                ns = "猜前三名";
                            }
                            if (tidd == 233) {
                                ns = "定位胆";
                            }
                            if (tidd == 234) {
                                ns = "大小";
                            }
                            if (tidd == 235) {
                                ns = "单双";
                            }

                            if (tidd == 499 || tidd == 517 || tidd == 739) {
                                ns = "趣味";
                            }
                            if (tidd == 501 || tidd == 519 || tidd == 741) {
                                ns = "任选";
                            }
                            if (tidd == 503 || tidd == 521 || tidd == 743) {
                                ns = "和值";
                            }
                            if (tidd == 505 || tidd == 523 || tidd == 745) {
                                ns = "盘面";
                            }
                            if (tidd == 507 || tidd == 525 || tidd == 747) {
                                ns = "五行";
                            }


                            if (tidd == 32 || tidd == 37) {
                                ns = "不定位";
                            }
                            if (tidd == 33 || tidd == 38) {
                                ns = "定位胆";
                            }
                            if (tidd == 53 || tidd == 54 || tidd == 57 || tidd == 58) {
                                ns = "二星";
                            }
                            if (tidd == 51 || tidd == 52 || tidd == 56 || tidd == 55) {
                                ns = "三星";
                            }
                            if (tidd == 34 || tidd == 39) {
                                ns = "大小单双";
                            }

                            if (tidd == 783) {
                                ns = "正码";
                            }
                            if (tidd == 784) {
                                ns = "特码";
                            }
                            if (tidd == 786) {
                                ns = "特码大小单双";
                            }
                            if (tidd == 787) {
                                ns = "生肖";
                            }
                            if (tidd == 789) {
                                ns = "特码波色";
                            }

                            gameType.setName(ns + "-" + name);
                            ListgameTypes.add(gameType);
                            Log.d("Game游戏7==", gameType.toString());
                        }


                        listener.onReceivedData(API_ID_GAME7, ListgameTypes, API_ID_ERROR);
                    }

                }


            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });


    }

    //获取用户的余额
    public void LoginUserAmount(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        final Call<UserAmount> userAmount = apiInterface.getUserAmount(1, reqkey, currentTimeMillis);
        Call<UserAmount> clone = userAmount.clone();
        listener.onRequestStart(API_ID_USERAMOUNT);
        clone.enqueue(new Callback<UserAmount>() {
            @Override
            public void onResponse(Call<UserAmount> call, retrofit2.Response<UserAmount> response) {
                if (response.code() == 200) {
                    List<UserAmount> amounts = new ArrayList<UserAmount>();
                    amounts.add(0, response.body());
                    listener.onReceivedData(API_ID_USERAMOUNT, amounts, API_ID_ERROR);
                    Gson gson = new Gson();
                    // UserAmount userAmount1 = gson.fromJson(response.body(), UserAmount.class);
                    System.out.println(" 用户的余额==" + response.body().toString());
                    listener.onRequestEnd(API_ID_USERAMOUNT);
                }

            }

            @Override
            public void onFailure(Call<UserAmount> call, Throwable t) {

            }
        });
    }

    //奖金详情
    public void GetPrizeDetails(final DataListener listener, int rows, int page, String sidx, String sord, int id, int rid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("rows", rows + "");
        map.put("page", page + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("id", id + "");
        map.put("rid", rid + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> prizeDetails = apiInterface.getPrizeDetails(1, rows, page, sidx, sord, id, rid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = prizeDetails.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                List<GamePrize> gamePrizeList = new ArrayList<GamePrize>();
                Map<String, Object> body = response.body();
                Log.d("获取奖金详情", body + "");
                double totalElements = 0;
                List<Object> con = null;
                if (body.size() > 0) {
                    for (Map.Entry<String, Object> entry : body.entrySet()) {
                        System.out.println("key==" + entry.getKey() + " " + entry.getValue());
                        if ("totalElements".equals(entry.getKey())) {
                            totalElements = (double) entry.getValue();
                        }
                        if ("content".equals(entry.getKey())) {
                            con = (List<Object>) entry.getValue();
                        }
                    }
                }
                if (totalElements > 0) {

                    for (int i = 0; i < con.size(); i++) {
                        List<Object> o = (List<Object>) con.get(i);
                        for (int i1 = 0; i1 < o.size(); i1 = i1 + 6) {
                            GamePrize gamePrize = new GamePrize();
                            String s1 = o.get(i1) + "";
                            String s = s1.substring(0, s1.length() - 2);
                            gamePrize.setGrid(Integer.parseInt(s));
                            gamePrize.setGameName(o.get(i1 + 1) + "");
                            gamePrize.setRuleType(o.get(i1 + 2) + "");
                            gamePrize.setRuleName(o.get(i1 + 3) + "");
                            gamePrize.setPrize(o.get(i1 + 4) + "");
                            gamePrize.setUserRate((Double) o.get(i1 + 5));
                            gamePrizeList.add(gamePrize);
                            Log.d("获取奖金详情String", gamePrize.toString());
                        }
                    }
                }
                listener.onReceivedData(API_ID_GAMEPRIZE, gamePrizeList, API_ID_ERROR);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
            }
        });
    }


    //游戏开奖记录
    public void getRecordList(final DataListener listener, int rows, int page, String sidx, String sord, int id, String period, String start, String end) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("rows", rows + "");
        map.put("page", page + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("id", id + "");
        map.put("period", period + "");
        map.put("start", start + "");
        map.put("end", end + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> recordList = apiInterface.getRecordList(1, rows, page, sidx, sord, id, period, start, end, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = recordList.clone();
        Log.d("游戏开奖记录请求完全体", clone.request().toString() + "");
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("游戏开奖记录", response.body().toString());
                    List<RecordList> rc = new ArrayList<RecordList>();
                    double totalElements = 0;
                    List<List<String>> con = null;
                    Map<String, Object> body = response.body();
                    List<String> c = new ArrayList<String>();
                    if (body.size() > 0) {
                        for (Map.Entry<String, Object> entry : body.entrySet()) {
                            System.out.println("key==" + entry.getKey() + " " + entry.getValue());
                            if ("totalElements".equals(entry.getKey())) {
                                totalElements = (double) entry.getValue();
                            }
                            if ("content".equals(entry.getKey())) {
                                con = (List<List<String>>) entry.getValue();
                            }
                        }
                        Log.d("游戏开奖记录totalElements==", totalElements + "");
                        if (totalElements > 0) {

                            for (int i = 0; i < con.size(); i++) {
                                Log.d("游戏开奖记录ConListrSize==", con.size() + "");
                                List<String> o = con.get(i);
                                String s = "";
                                String s1 = String.valueOf(o.get(0));
                                String s2 = s1.substring(0, s1.length() - 2);
                                RecordList recordList1 = new RecordList();
                                recordList1.setGid(Integer.parseInt(s2));
                                recordList1.setGameName(o.get(1));
                                recordList1.setPeriod(o.get(2));
                                recordList1.setWinningNumber(o.get(3));
                                recordList1.setDrawDate(o.get(4));
                                rc.add(recordList1);
                                Log.d("游戏开奖记录String==", recordList1.toString() + "");
                            }
                        }
                    }
                    listener.onReceivedData(API_ID_RECORDLIST, rc, API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //找回密码
    public void getBackPassWord(String u, String p) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("u", u);
        map.put("p", p);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
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
        Map<String, String> map = new HashMap<>();
        map.put("u", u);
        map.put("p", p);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
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
    public void getActivityCheck(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getActivityCheck(1, id, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("领取活动奖金", response.body().toString());
                    listener.onReceivedData(API_ID_DRAWMONEY, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //领取活动奖金AID
    public void getActivityCheckAid(final DataListener listener, int id, int alid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("alid", alid + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getActivityCheckAid(1, id, alid, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("领取活动奖金AID", response.body().toString());
                    listener.onReceivedData(API_ID_RECEIVE, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //修改用户昵称
    public void getUpdateNickName(final DataListener listener, String nickName) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("nickname", nickName);
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Log.d("用户名加密的东西", reqkey);
        Call<RestultInfo> nickNameChange = apiInterface.getNickNameChange(1, nickName, reqkey, currentTimeMillis);
        String s = nickNameChange.request().toString();
        Log.d("修改用户昵称请求完全体====", s);
        Call<RestultInfo> clone = nickNameChange.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("修改昵称返回的", response.body().getMsg() + "");
                    listener.onReceivedData(API_ID_UPDATENICKNAME, response.body(), API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });


    }

    //强制修改初始密码
    public void getUpdateFirstPwd(final DataListener listener, String p0, String p1, String p2, String email, String q, String a) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("p0", p0);
        map.put("p1", p1);
        map.put("p2", p2);
        map.put("email", email);
        map.put("q", q);
        map.put("a", a);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> updateFirstPwd = apiInterface.getUpdateFirstPwd(1, p0, p1, p2, email, q, a, reqkey, currentTimeMillis);
        String s = updateFirstPwd.request().toString();
        Log.d("强制修改初始密码的结果请求", s);
        Call<RestultInfo> clone = updateFirstPwd.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("强制修改初始密码的结果Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("强制修改初始密码的结果", response.body().toString());
                    listener.onReceivedData(API_ID_UPDATAFIRSTPWD, response.body(), API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Log.d("强制修改初始密码的结果Error", t.toString());
            }
        });

    }

    //获取安全问题
    public void getSafeQues(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<List<Object>>> clone = apiInterface.getSafeQues(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, retrofit2.Response<List<List<Object>>> response) {
                if (response.code() == 200) {
                    String ques = response.body().toString();
                    String substring = ques.substring(1, ques.length() - 1);
                    List<List<Object>> li = response.body();
                    List<String> Safe = new ArrayList<String>();
                    for (int i = 0; i < li.size(); i++) {
                        List<Object> o = li.get(i);
                        if (o.size() > 0) {
                            double id = (double) o.get(0);
                            String name = (String) o.get(1);
                            Safe.add(name);
                        }
                    }
                    listener.onReceivedData(API_ID_SAFEQUES, Safe, API_ID_ERROR);
                    Log.d("安全问题是2==", ques);
                }

            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {

            }
        });

    }

    //获取首页的公告或者滚动栏的内容
    public void getHomeNotice(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> clone = apiInterface.getHomeNotice(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String toString = response.body().toString();
                    Log.d("首页的公告==", toString);
                    boolean rc = false;
                    String content = null;
                    double sticky = 0;
                    Map<String, Object> others = new HashMap<>();
                    Map<String, Object> map = response.body();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    if (rc) {
                        if (others.size() > 0) {
                            for (Map.Entry<String, Object> entry : others.entrySet()) {
                                if ("content".equals(entry.getKey())) {
                                    content = (String) entry.getValue();
                                }
                                if ("sticky".equals(entry.getKey())) {
                                    sticky = (double) entry.getValue();
                                }
                            }
                        }
                    }
                    List<String> noti = new ArrayList<String>();
                    noti.add(content);
                    noti.add(RxUtils.getInstance().getInt(sticky) + "");
                    listener.onReceivedData(API_ID_HOMENOTICE, noti, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //修改登录密码
    public void getUpDataPwd(final DataListener listener, String p0, String p1) {
        long currentTimeMillis = System.currentTimeMillis();
        String re1 = "AppClient=1&p0=" + p0 + "&p1=" + p1 + "&t=" + currentTimeMillis;
        Map<String, String> map = new HashMap<>();
        map.put("p0", p0 + "");
        map.put("p1", p1 + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getUpDatePwd(1, p0, p1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    listener.onReceivedData(API_ID_UPDATAPWD, response.body(), API_ID_ERROR);
                    Log.d("修改密码的结果", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //验证安全密码
    public void getCheckSafePwd(final DataListener listener, String p) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("p", p);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getCheckSafePwd(1, p, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    listener.onReceivedData(API_ID_SAFEPWD, response.body(), API_ID_ERROR);
                    Log.d("验证安全密码的结果", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //保存安全问题
    public void getSaveSafeQus(final DataListener listener, String q, String a) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("q", q);
        map.put("a", a);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> infoCall = apiInterface.getSaveSafeQues(1, q, a, reqkey, currentTimeMillis).clone();
        infoCall.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("验证安全密码", response.body().toString());
                    listener.onReceivedData(API_ID_PWDPROTACT, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取绑定的银行卡的数据
    public void getCardDatas(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> clone = apiInterface.getCardDatas(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取绑定的银行卡的银行数据S", s);
                    Map<String, Object> map = response.body();
                    List<List<Object>> cards = new ArrayList<>();
                    List<List<Object>> banks = new ArrayList<>();
                    List<List<Object>> provincials = new ArrayList<>();
                    boolean locked = false;
                    List<List<CardsData>> cs = new ArrayList<List<CardsData>>();
                    List<CardsData> cardss = new ArrayList<CardsData>();
                    List<CardsData> bankss = new ArrayList<CardsData>();
                    List<CardsData> provincess = new ArrayList<CardsData>();
                    List<CardsData> lockedss = new ArrayList<CardsData>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("cards".equals(entry.getKey())) {
                                cards = (List<List<Object>>) entry.getValue();
                            }
                            if ("banks".equals(entry.getKey())) {
                                banks = (List<List<Object>>) entry.getValue();
                            }
                            if ("provincials".equals(entry.getKey())) {
                                provincials = (List<List<Object>>) entry.getValue();
                            }
                            if ("locked".equals(entry.getKey())) {
                                locked = (boolean) entry.getValue();

                            }
                        }
                    }
                    for (int i = 0; i < cards.size(); i++) {
                        List<Object> o = cards.get(i);
                        if (o.size() > 0) {
                            CardsData cardsData = new CardsData();
                            double cardid = (double) o.get(0);
                            String holders_name = (String) o.get(1);
                            String bank_name = (String) o.get(2);
                            String account_number = (String) o.get(3);
                            String binding_time = (String) o.get(4);
                            cardsData.setCardid(RxUtils.getInstance().getInt(cardid));
                            cardsData.setHolders_name(holders_name);
                            cardsData.setBank_name(bank_name);
                            cardsData.setAccount_number(account_number);
                            cardsData.setBinding_time(binding_time);
                            cardss.add(cardsData);
                        }
                    }
                    for (int i = 0; i < banks.size(); i++) {
                        List<Object> o = banks.get(i);
                        if (o.size() > 0) {
                            CardsData cardsData = new CardsData();
                            double bankid = (double) o.get(0);
                            String bankname = (String) o.get(1);
                            cardsData.setBankid(RxUtils.getInstance().getInt(bankid));
                            cardsData.setBankname(bankname);
                            bankss.add(cardsData);
                        }
                    }
                    for (int i = 0; i < provincials.size(); i++) {
                        List<Object> o = provincials.get(i);
                        if (o.size() > 0) {
                            CardsData cardsData = new CardsData();
                            double provinceId = (double) o.get(0);
                            String provincename = (String) o.get(1);
                            cardsData.setProvince_id(RxUtils.getInstance().getInt(provinceId));
                            cardsData.setProvincename(provincename);
                            provincess.add(cardsData);
                        }
                    }
                    CardsData cardsData = new CardsData();
                    cardsData.setLocked(locked);
                    lockedss.add(cardsData);
                    cs.add(cardss);
                    cs.add(bankss);
                    cs.add(provincess);
                    cs.add(lockedss);

                    listener.onReceivedData(API_ID_CARDDATAS, cs, API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //验证银行卡号
    public void getCheckCardNum(final DataListener listener, String name, String card) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("card", card);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> checkBankCardNum = apiInterface.getCheckBankCardNum(1, name, card, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = checkBankCardNum.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("验证卡号返回的", response.body().toString());
                    listener.onReceivedData(API_ID_CHECKCAEDNUM, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //获得省市级联动
    public void getPrivens(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<List<Object>>> objectCall = apiInterface.getPrivens(1, id, reqkey, currentTimeMillis).clone();
        objectCall.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, retrofit2.Response<List<List<Object>>> response) {
                if (response.code() == 200) {
                    String string = response.body().toString();
                    Log.d("获取省市联动", string);
                    List<List<Object>> list = response.body();
                    List<CardsData> city = new ArrayList<CardsData>();
                    for (int i = 0; i < list.size(); i++) {
                        List<Object> o = list.get(i);
                        if (o.size() > 0) {
                            CardsData c = new CardsData();
                            double city_id = (double) o.get(0);
                            String cityName = (String) o.get(1);
                            c.setCity_id(RxUtils.getInstance().getInt(city_id));
                            c.setCityName(cityName);
                            city.add(c);
                        }
                    }
                /*    String citys = string.substring(1, string.length() - 1);
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
                    }*/
                    listener.onReceivedData(API_ID_GETCITYS, city, API_ID_ERROR);


                }

            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {

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
        Map<String, String> map = new HashMap<>();
        map.put("bank", bank + "");
        map.put("province_id", province_id + "");
        map.put("province", province + "");
        map.put("city_id", city_id + "");
        map.put("city", city + "");
        map.put("branch", branch + "");
        map.put("name", name + "");
        map.put("card", card + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> saveBankCard = apiInterface.getSaveBankCard(1, bank, province_id, province, city_id, city, branch, name, card, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = saveBankCard.clone();
        Log.d("保存银行卡请求完全体", clone.request().toString());
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("保存银行卡返回的数据Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("保存银行卡返回的数据", response.body().toString());
                    listener.onReceivedData(API_ID_SAVECARD, response.body(), API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取提现数据
    public void getWithDrawDatas(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> clone = apiInterface.getWithDrawData(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("获取提现数据", response.body().toString());
                    Map<String, Object> map = response.body();
                    List<List<Object>> cards = new ArrayList<>();
                    boolean freeze = false;
                    boolean notime = false;
                    String start = null;
                    String end = null;
                    double nums = 0;
                    double amounts = 0;
                    double min = 0;
                    double max = 0;
                    List<List<WithDraw>> ws = new ArrayList<List<WithDraw>>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("freeze".equals(entry.getKey())) {
                                freeze = (boolean) entry.getValue();
                            }
                            if ("notime".equals(entry.getKey())) {
                                notime = (boolean) entry.getValue();
                            }
                            if ("start".equals(entry.getKey())) {
                                start = (String) entry.getValue();
                            }
                            if ("end".equals(entry.getKey())) {
                                end = (String) entry.getValue();
                            }
                            if ("nums".equals(entry.getKey())) {
                                nums = (double) entry.getValue();
                            }
                            if ("amounts".equals(entry.getKey())) {
                                amounts = (double) entry.getValue();
                            }
                            if ("min".equals(entry.getKey())) {
                                min = (double) entry.getValue();
                            }
                            if ("max".equals(entry.getKey())) {
                                max = (double) entry.getValue();
                            }
                            if ("cards".equals(entry.getKey())) {
                                cards = (List<List<Object>>) entry.getValue();
                            }
                        }
                    }
                    List<WithDraw> w = new ArrayList<WithDraw>();
                    WithDraw withDraw = new WithDraw();
                    withDraw.setFreeze(freeze);
                    withDraw.setNotime(notime);
                    withDraw.setStart(start);
                    withDraw.setEnd(end);
                    withDraw.setNums(RxUtils.getInstance().getInt(nums));
                    withDraw.setAmounts(BigDecimal.valueOf(amounts));
                    withDraw.setMin(min);
                    withDraw.setMax(max);
                    w.add(withDraw);
                    ws.add(w);
                    List<WithDraw> w2 = new ArrayList<WithDraw>();

                    for (int i = 0; i < cards.size(); i++) {
                        List<Object> o = cards.get(i);
                        if (o.size() > 0) {
                            WithDraw withD = new WithDraw();
                            double aid = (double) o.get(0);
                            String cardNumber = (String) o.get(1);
                            String holders_name = (String) o.get(2);
                            withD.setAid(RxUtils.getInstance().getInt(aid));
                            withD.setCardNumber(cardNumber);
                            withD.setHolders_name(holders_name);
                            w2.add(withD);
                        }
                    }
                    ws.add(w2);
                    listener.onReceivedData(API_ID_WITHDRAW, ws, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });


    }

    //添加提现申请
    //  应用场景：提现验证通过后创建提现申请
    public void getWithDrawCreates(final DataListener listener, int aid, BigDecimal amount) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("aid", aid + "");
        map.put("amount", amount + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getWithDrawCreate(1, aid, amount, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("提交申请返回的", response.body().toString());
                    listener.onReceivedData(API_ID_WITHDRAWCREATE, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //修改安全密码
    public void getUpDataSafePwd(final DataListener listener, String p0, String p1, String email) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("p0", p0);
        map.put("p1", p1);
        map.put("email", email);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getUpDataSafePwd(1, p0, p1, email, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("修改安全密码返回的数据", response.body().toString());
                    listener.onReceivedData(API_ID_UPDATASAFEPWD, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });


    }

    //重置安全密码 或者 登陆密码
    public void getResetPwd(final DataListener listener, int type, String p) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("p", p + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getResetPwd(1, type, p, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("重置安全密码或者登陆密码", response.body().toString());
                    listener.onReceivedData(API_ID_RESETPWD, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //绑定银行卡锁定
    public void getBingCardLock(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getBindingCardLock(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    listener.onReceivedData(API_ID_CARDLOCK, response.body(), API_ID_ERROR);
                    Log.d("绑定银行卡锁定", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取聊天用户
    public void getChatUser(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> clone = apiInterface.getChatUsers(1, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
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


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("获取聊天用户的Error", t.toString());
            }
        });
    }

    //获得消息列表
    public void getChatList(final DataListener listener, int page, int rows, String sidx, String sord, int send_uid, String from, String to) {
        long currentTimeMillis = System.currentTimeMillis();

        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("send_uid", send_uid + "");
        map.put("from", from + "");
        map.put("to", to + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> clone = apiInterface.getChatList(1, page, rows, sidx, sord, send_uid, from, to, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("获取消息列表", response.body().toString());
                    Map<String, Object> map = response.body();
                    double number = 0;
                    List<Object> content = new ArrayList<>();
                    List<Message> msgs = new ArrayList<Message>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("content".equals(entry.getKey())) {
                                content = (List<Object>) entry.getValue();
                            }
                            if ("number".equals(entry.getKey())) {
                                number = (double) entry.getValue();
                            }
                        }
                    }
                    if (number > 0) {
                        for (int i = content.size(); i > 0; i--) {
                            List<Object> o = (List<Object>) content.get(i - 1);
                            Message msg = new Message();
                            double o1 = (double) o.get(0);
                            String o2 = (String) o.get(1);
                            String o3 = (String) o.get(2);
                            String o4 = (String) o.get(3);
                            String o5 = (String) o.get(4);
                            String o6 = (String) o.get(5);
                            double o7 = (double) o.get(6);
                            double o8 = (double) o.get(7);
                            msg.setChat_id(RxUtils.getInstance().getInt(o1));
                            msg.setTitle(o2);
                            msg.setContent(o3);
                            msg.setSname(o4);
                            msg.setUname(o5);
                            msg.setChat_date(o6);
                            msg.setReaded(RxUtils.getInstance().getInt(o7));
                            msg.setIs_alert(RxUtils.getInstance().getInt(o8));
                            msgs.add(msg);
                        }
                    }
                    listener.onReceivedData(API_ID_CHATLIST, msgs, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //删除聊天消息
    public void getDeleteChatMsg(DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getDeleteChatMsg(1, id, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("删除聊天消息", response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //加载聊天信息
    public void getChatMsg(DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> clone = apiInterface.getChatMsg(1, id, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("加载聊天信息", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //发送聊天信息
    public void getSendMsg(final DataListener listener, int id, String title, String msg) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("title", title + "");
        map.put("msg", msg + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> clone = apiInterface.getSendMsg(1, id, title, msg, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("发送聊天信息", response.body().toString());
                    listener.onReceivedData(API_ID_SENDMSG, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Log.d("发送聊天信息Error", t.toString());
            }
        });
    }

    //轮询获取新消息
    public void getNewMsg(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<List<Object>>> clone = apiInterface.getNewMsg(1, id, reqkey, currentTimeMillis).clone();
        clone.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, retrofit2.Response<List<List<Object>>> response) {
                if (response.code() == 200) {
                    Log.d("轮询获取新消息", response.body().toString());
                    List<List<Object>> list = response.body();
                    List<Message> messages = new ArrayList<Message>();
                    for (int i = 0; i < list.size(); i++) {
                        List<Object> megs = list.get(i);
                        double id = (double) megs.get(0);
                        String uname = (String) megs.get(1);
                        String date = (String) megs.get(2);
                        String content = (String) megs.get(3);
                        Message message = new Message();
                        message.setChat_id(RxUtils.getInstance().getInt(id));
                        message.setUname(uname);
                        message.setChat_date(date);
                        message.setContent(content);
                        messages.add(message);
                    }
                    listener.onReceivedData(API_ID_GETNEWMSG, messages, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
                Log.d("轮询获取新消息Error", t.toString());
            }
        });
    }

    //查询投注记录
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
        Call<Map<String, Object>> bettingRecordList = apiInterface.getBettingRecordList(1, page, rows, sidx, sord, from, to, id, rid, status, rebuy, period, reqkey, currentTimeMillis);
        String s = bettingRecordList.request().toString();
        Log.d("查询投注记录请求完全体====", s);
        Call<Map<String, Object>> clone = bettingRecordList.clone();

        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String touZhu = response.body().toString();
                    Log.d("查询投注记录====", touZhu);
                    List<TouZhu> ts = new ArrayList<TouZhu>();
                    double totalElements = 0;
                    List<List<Object>> con = new ArrayList<List<Object>>();
                    Map<String, Object> map = response.body();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("totalElements".equals(entry.getKey())) {
                                totalElements = (double) entry.getValue();
                            }
                            if ("content".equals(entry.getKey())) {
                                con = (List<List<Object>>) entry.getValue();
                            }
                        }
                    }
                    if (totalElements > 0) {
                        for (int i = 0; i < con.size(); i++) {
                            List<Object> o = con.get(i);
                            double bid = (double) o.get(0);
                            String name = (String) o.get(1);
                            String rname = (String) o.get(2);
                            String period = (String) o.get(3);
                            String picked_text = (String) o.get(4);
                            double amount = (double) o.get(5);
                            double prize = (double) o.get(6);
                            String re_buy = (String) o.get(7);
                            String buy_time = (String) o.get(8);
                            double status = (double) o.get(9);
                            String winning_numbers = (String) o.get(10);
                            TouZhu t = new TouZhu();
                            t.setBid(RxUtils.getInstance().getInt(bid));
                            t.setName(name);
                            t.setRname(rname);
                            t.setPeriod(period);
                            t.setPiced_text(picked_text);
                            t.setAmount(amount);
                            t.setPrize(prize);
                            t.setRe_buy(re_buy);
                            t.setBuy_time(buy_time);
                            t.setStatus(RxUtils.getInstance().getInt(status));
                            t.setWinning_numbers(winning_numbers);
                            ts.add(t);
                        }
                    }
                    listener.onReceivedData(API_ID_TOUZHUSEAR, ts, API_ID_ERROR);

                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
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
        Call<Map<String, Object>> keepNum = apiInterface.getKeepNum(1, page, rows, sidx, sord, from, to, id, rid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = keepNum.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String toString = response.body().toString();
                    Log.d("追号记录完全体=", toString);
                    Map<String, Object> map = response.body();
                    List<Object> content = new ArrayList<>();
                    double totalElements = 0;
                    List<ZhuiHao> zhuiHaos = new ArrayList<ZhuiHao>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("totalElements".equals(entry.getKey())) {
                                totalElements = (double) entry.getValue();
                            }
                            if ("content".equals(entry.getKey())) {
                                content = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    if (totalElements > 0) {
                        for (int i = 0; i < content.size(); i++) {
                            List<Object> cn = (List<Object>) content.get(i);
                            ZhuiHao z = new ZhuiHao();
                            z.setId(RxUtils.getInstance().getInt(cn.get(0)));
                            z.setNumber((String) cn.get(1));
                            z.setPurchase_date((String) cn.get(2));
                            z.setGname((String) cn.get(3));
                            z.setRname((String) cn.get(4));
                            z.setStart_period((String) cn.get(5));
                            z.setPeriods(RxUtils.getInstance().getInt(cn.get(6)));
                            z.setPurchase_periods(RxUtils.getInstance().getInt(cn.get(7)));
                            z.setPicked_numbers((String) cn.get(8));
                            z.setMode(RxUtils.getInstance().getInt(cn.get(9)));
                            z.setAmount((Double) cn.get(10));
                            z.setPurchase_amount((Double) cn.get(11));
                            z.setCancel_amount((Double) cn.get(12));
                            z.setPrize_num(RxUtils.getInstance().getInt(cn.get(13)));
                            z.setPrize_amount((Double) cn.get(14));
                            z.setBids((String) cn.get(15));
                            z.setStatus(RxUtils.getInstance().getInt(cn.get(16)));
                            Log.d("查询追号的Bean", z.toString());
                            zhuiHaos.add(z);

                        }
                    }
                   /* String substring = toString.substring(toString.indexOf("content=[") + 9, toString.indexOf("], number"));
                    Log.d("追号记录SubString=", substring);

                    if (substring.length() > 10) {
                        String[] sp = substring.split(", ");

                        for (int i = 0; i < sp.length; i = i + 17) {
                            //  Log.d("追号记录截取1", sp[i]);
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

                        Log.d("查询追号返回数据", toString);
                    }*/
                    listener.onReceivedData(API_ID_ZHUIHAO, zhuiHaos, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("查询追号返回数据异常", t.toString());
            }
        });
    }

    //查询追号详情
    public void getKeepNumDeTails(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> keepNumDetails = apiInterface.getKeepNumDetails(id, 1, reqkey, currentTimeMillis);
        Call<Object> clone = keepNumDetails.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
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
        Call<Map<String, Object>> keepNumBet = apiInterface.getKeepNumBet(1, page, rows, sidx, sord, id, reqkey, l);
        Call<Map<String, Object>> clone = keepNumBet.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("查询追号投注记录", s);
                    Map<String, Object> map = response.body();
                    List<List<Object>> content = null;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("content".equals(entry.getKey())) {
                                content = (List<List<Object>>) entry.getValue();
                            }
                        }
                    }
                    List<ZhuiHao> zhs = new ArrayList<ZhuiHao>();
                    for (int i = 0; i < content.size(); i++) {
                        List<Object> l = content.get(i);
                        ZhuiHao zh = new ZhuiHao();
                        zh.setId(RxUtils.getInstance().getInt(l.get(0)));
                        zh.setNumber((String) l.get(1));
                        zh.setPrize_num(RxUtils.getInstance().getInt(l.get(2)));
                        zh.setStatus(RxUtils.getInstance().getInt(l.get(3)));
                        Log.d("查询追号投注记录Bean", zh.toString());
                        zhs.add(zh);
                    }

                    listener.onReceivedData(API_ID_ZHTZDETAIL, zhs, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //批量撤销投注单
    public void getLotteryBetRevoke(final DataListener listener, String ids) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> lotteryBetRevoke = apiInterface.getLotteryBetRevoke(1, ids, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = lotteryBetRevoke.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("批量撤销投注单Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("批量撤销投注单", response.body().toString());
                    listener.onReceivedData(API_ID_CACEL, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Toasty.info(MyApp.getInstance().getApplicationContext(), "服务器响应超时", 2000).show();
                //  Log.d("批量撤销投注单Error", t.toString() + "");
            }
        });

    }

    //查询个人报表彩票帐变
    public void getAccountChangeList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int id, int type, int stype, int model) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("id", id + "");
        maps.put("type", type + "");
        maps.put("stype", stype + "");
        maps.put("model", model + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> accountChangeList = apiInterface.getAccountChangeList(1, page, rows, sidx, sord, from, to, id, type, stype, model, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = accountChangeList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("查询个人报表彩票帐变", s + "");
                    Map<String, Object> map = response.body();
                    Map<String, Object> userdata = response.body();
                    List<Object> r = new ArrayList<>();
                    List<List<AccountChange>> account = new ArrayList<List<AccountChange>>();
                    double records = 0;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                r = (List<Object>) entry.getValue();
                            }
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                        }
                    }

                    List<AccountChange> acs1 = new ArrayList<AccountChange>();
                    double amount = 0;
                    if (userdata.size() > 0) {
                        AccountChange ac = new AccountChange();
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("amount".equals(entry.getKey())) {
                                amount = (double) entry.getValue();
                            }
                        }
                        ac.setAmountss(amount);
                        acs1.add(ac);
                    }
                    account.add(acs1);
                    List<AccountChange> acs2 = new ArrayList<AccountChange>();
                    for (int i = 0; i < r.size(); i++) {
                        Map<String, Object> m = (Map<String, Object>) r.get(i);
                        if (m.size() > 0) {
                            List<Object> cell = new ArrayList<>();
                            for (Map.Entry<String, Object> entry : m.entrySet()) {
                                if ("cell".equals(entry.getKey())) {
                                    cell = (List<Object>) entry.getValue();
                                }
                            }
                            AccountChange acc = new AccountChange();
                            acc.setUname((String) cell.get(0));
                            acc.setDate((String) cell.get(1));
                            acc.setStype(Integer.parseInt((String) cell.get(2)));
                            acc.setGname((String) cell.get(3));
                            acc.setRname((String) cell.get(4));
                            acc.setPeriod((String) cell.get(5));
                            acc.setModel(Integer.parseInt((String) cell.get(6)));
                            acc.setAmount(Double.parseDouble((String) cell.get(7)));
                            acc.setAmounts(Double.parseDouble((String) cell.get(8)));
                            acs2.add(acc);
                        }
                    }
                    account.add(acs2);
                    listener.onReceivedData(API_ID_ACCOUNTCHANGE, account, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });


    }

    //52 个人报表充提记录
    public void getReChargeWithDrawList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int type) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("from", from + "");
        map.put("to", to + "");
        map.put("type", type + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> reChargeWithDrawList = apiInterface.getReChargeWithDrawList(1, page, rows, sidx, sord, from, to, type, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = reChargeWithDrawList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("个人报表充提记录", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> userdata = new HashMap<>();
                    List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
                    List<Object> cell = new ArrayList<>();
                    List<List<CunQu>> cqs = new ArrayList<List<CunQu>>();
                    double income = 0;
                    double expend = 0;
                    double records = 0;
                    String id = null;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Map<String, Object>>) entry.getValue();
                            }
                        }
                    }
                    if (userdata.size() > 0) {
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("income".equals(entry.getKey())) {
                                income = (double) entry.getValue();
                            }
                            if ("expend".equals(entry.getKey())) {
                                expend = (double) entry.getValue();
                            }
                        }
                    }

                    List<CunQu> ccs = new ArrayList<CunQu>();
                    CunQu c = new CunQu();
                    c.setIncomes(income);
                    c.setExpengs(expend);
                    ccs.add(c);
                    cqs.add(ccs);
                    List<CunQu> cs = new ArrayList<CunQu>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            Map<String, Object> o = rows.get(i);
                            if (o.size() > 0) {
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (String) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<Object>) entry.getValue();
                                        if (cell.size() > 0) {
                                            CunQu cunQu = new CunQu();
                                            cunQu.setId(Integer.parseInt(id));
                                            cunQu.setSerial_number((String) cell.get(0));
                                            cunQu.setUname((String) cell.get(1));
                                            cunQu.setDate((String) cell.get(2));
                                            cunQu.setStype((Integer.parseInt((String) cell.get(3))));
                                            cunQu.setIncome(Double.parseDouble((String) cell.get(4)));
                                            cunQu.setExpend(Double.parseDouble(((String) cell.get(5))));
                                            cunQu.setAmount(Double.parseDouble(((String) cell.get(6))));
                                            cunQu.setStatus((Integer.parseInt((String) cell.get(7))));
                                            cunQu.setDetial((String) cell.get(8));
                                            cs.add(cunQu);
                                            Log.d("个人报表充提记录String", cunQu.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    cqs.add(cs);
                    listener.onReceivedData(API_ID_RECHARGEDRAW, cqs, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //个人报表彩票投注

    public void getBettingList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int id, int rid, int status) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("id", id + "");
        maps.put("rid", rid + "");
        maps.put("status", status + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> bettingList = apiInterface.getBettingList(1, page, rows, sidx, sord, from, to, id, rid, status, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = bettingList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("个人报表彩票投注Content", s);
                    double totalElements = 0;
                    List<Object> content = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : response.body().entrySet()) {
                        if ("totalElements".equals(entry.getKey())) {
                            totalElements = (double) entry.getValue();
                        }
                        if ("content".equals(entry.getKey())) {
                            content = (List<Object>) entry.getValue();
                        }
                    }
                    List<UserTeamBetting> userBetting = new ArrayList<UserTeamBetting>();
                    if (totalElements > 0) {
                        for (int i = 0; i < content.size(); i++) {
                            List<Object> cn = (List<Object>) content.get(i);
                            UserTeamBetting us = new UserTeamBetting();
                            us.setBid(RxUtils.getInstance().getInt(cn.get(0)));
                            us.setUid(RxUtils.getInstance().getInt(cn.get(1)));
                            us.setUname((String) cn.get(2));
                            us.setDate((String) cn.get(3));
                            us.setGname((String) cn.get(4));
                            us.setPeriod((String) cn.get(5));
                            us.setRname((String) cn.get(6));
                            us.setPicked_text((String) cn.get(7));
                            us.setAmount((Double) cn.get(8));

                            if (cn.get(9) == null) {
                                us.setPrize(0.0);
                            } else {
                                us.setPrize((Double) cn.get(9));
                            }

                            us.setWinning_number((String) cn.get(10));
                            Log.d("个人报表彩票投注Status", RxUtils.getInstance().getInt(cn.get(11)) + "");
                            us.setStatus(RxUtils.getInstance().getInt(cn.get(11)));
                            userBetting.add(us);
                        }

                    }
                    listener.onReceivedData(API_ID_USERBETTING, userBetting, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //个人报表彩票盈亏
    public void getProfitLossList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int gtype) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("gtype", gtype + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> profitLossList = apiInterface.getProfitLossList(1, page, rows, sidx, sord, from, to, gtype, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = profitLossList.clone();
        Log.d("个人报表彩票盈亏请求完全体", clone.request().toString());
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Headers headers = response.headers();
                    String s = response.body().toString();
                    Log.d("个人报表彩票盈亏", s);
                    Map<String, Object> map = response.body();
                    double records = 0;

                    List<Object> r = new ArrayList<>();
                    Map<String, Object> rows = new HashMap<>();
                    Map<String, Object> userdata = new HashMap<>();
                    List<List<LotteryLoss>> l = new ArrayList<List<LotteryLoss>>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                r = (List<Object>) entry.getValue();
                            }
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    List<LotteryLoss> losses = new ArrayList<LotteryLoss>();
                    for (int j = 0; j < r.size(); j++) {
                        LotteryLoss ls = new LotteryLoss();
                        Map<String, Object> rs = (Map<String, Object>) r.get(j);
                        String id = null;
                        String uname = null;
                        String recharge_amount = null;
                        String withdraw_amount = null;
                        String betting_amount = null;
                        String rebate_amount = null;
                        String winning_amount = null;
                        String activity_amount = null;
                        String activity_amount2 = null;
                        String profit_loss = null;
                        String type = null;
                        String counts = null;
                        List<Object> cell = new ArrayList<>();
                        if (rs.size() > 0) {
                            for (Map.Entry<String, Object> entry : rs.entrySet()) {
                                if ("id".equals(entry.getKey())) {
                                    id = (String) entry.getValue();
                                }
                                if ("cell".equals(entry.getKey())) {
                                    cell = (List<Object>) entry.getValue();
                                }
                            }
                        }
                        for (int i = 0; i < cell.size(); i++) {
                            uname = (String) cell.get(0);
                            recharge_amount = (String) cell.get(1);
                            withdraw_amount = (String) cell.get(2);
                            betting_amount = (String) cell.get(3);
                            rebate_amount = (String) cell.get(4);
                            winning_amount = (String) cell.get(5);
                            activity_amount = (String) cell.get(6);
                            activity_amount2 = (String) cell.get(7);
                            profit_loss = (String) cell.get(8);
                            type = (String) cell.get(9);
                            counts = (String) cell.get(10);
                        }
                        ls.setUname(uname);
                        ls.setBetting_amount(Double.parseDouble(betting_amount));
                        ls.setRebate_amount(Double.parseDouble(rebate_amount));
                        ls.setWinning_amount(Double.parseDouble(winning_amount));
                        ls.setProfit_loss(Double.parseDouble(profit_loss));
                        ls.setType(Integer.parseInt(type));
                        losses.add(ls);
                    }

                    double betting_amount = 0;
                    double winning_amount = 0;
                    double activity_amount = 0;
                    double activity_amount2 = 0;
                    double recharge_amount = 0;
                    double withdraw_amount = 0;
                    double rebate_amount = 0;
                    double profit_loss = 0;
                    List<LotteryLoss> loss = new ArrayList<LotteryLoss>();
                    if (userdata.size() > 0) {
                        LotteryLoss ls1 = new LotteryLoss();
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("betting_amount".equals(entry.getKey())) {
                                betting_amount = (double) entry.getValue();
                            }
                            if ("winning_amount".equals(entry.getKey())) {
                                winning_amount = (double) entry.getValue();
                            }
                            if ("activity_amount".equals(entry.getKey())) {
                                activity_amount = (double) entry.getValue();
                            }
                            if ("activity_amount2".equals(entry.getKey())) {
                                activity_amount2 = (double) entry.getValue();
                            }
                            if ("recharge_amount".equals(entry.getKey())) {
                                recharge_amount = (double) entry.getValue();
                            }
                            if ("withdraw_amount".equals(entry.getKey())) {
                                withdraw_amount = (double) entry.getValue();
                            }
                            if ("rebate_amount".equals(entry.getKey())) {
                                rebate_amount = (double) entry.getValue();
                            }
                            if ("profit_loss".equals(entry.getKey())) {
                                profit_loss = (double) entry.getValue();
                            }
                        }
                        ls1.setBetting_amounts(betting_amount);
                        ls1.setProfit_loss(profit_loss);
                        loss.add(ls1);
                    }
                    l.add(loss);
                    l.add(losses);


                    listener.onReceivedData(API_ID_PROFITLOSS, l, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //55 获取大数据投注内容
    public void getGetMoreData(DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Object> getMoreData = apiInterface.getGetMoreData(1, id, reqkey, currentTimeMillis);
        Call<Object> clone = getMoreData.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("获取大数据投注内容", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    //获取验证码图片
    public void getCaptCha(final DataListener listener, long t) {
        String httpUrl = ApiInterface.HOST + "/captcha?t=" + t;
        OkGo.get(httpUrl)
                .tag(this)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, okhttp3.Call call, Response response) {
                        listener.onReceivedData(API_ID_IMAGECHECK, bitmap, API_ID_ERROR);
                    }
                });

    }

    //验证码校验
    public void getCaptChaCheck(final DataListener listener, String u, String c) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("u", u);
        maps.put("c", c);
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        String httpUrl = ApiInterface.HOST + "/captcha-check?AppClient=1&u=" + u + "&c=" + c + "&reqkey=" + reqkey + "&t=" + currentTimeMillis;
        OkGo.post(httpUrl)
                .tag(this)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        Gson gson = new Gson();
                        RestultInfo restultInfo = gson.fromJson(s, RestultInfo.class);
                        Log.d("验证返回", s);
                        listener.onReceivedData(API_ID_IMAGECHECKS, restultInfo, API_ID_ERROR);
                    }
                });

/*
        Call<Object> captChaCheck = apiInterface.getCaptChaCheck(1, u, c, reqkey, currentTimeMillis);
        Call<Object> clone = captChaCheck.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                int code = response.code();
                sessionLoginId = getSessionCookie(response.headers().get("Set-Cookie"));
                System.out.println("验证码校验Cookie==" + sessionLoginId);
                Gson gson = new GsonBuilder().setLenient().create();
                Log.d("验证码校验Code", code + "");
                Log.d("验证码校验", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });*/
    }


    //52 个人报表充提记录
    public void getTeamReChargeWithDrawList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, String name, int type, int team) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("from", from + "");
        map.put("to", to + "");
        map.put("name", name + "");
        map.put("type", type + "");
        map.put("team", team + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> reChargeWithDrawList = apiInterface.getTeamReChargeWithDrawList(1, page, rows, sidx, sord, from, to, name, type, team, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = reChargeWithDrawList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("团队报表充提记录", s);
                    List<List<CunQu>> cqs = new ArrayList<List<CunQu>>();
                    List<CunQu> ccs = new ArrayList<CunQu>();
                    Map<String, Object> map = response.body();
                    Map<String, Object> userdata = new HashMap<>();
                    double records = 0;
                    List<Object> rows = new ArrayList<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    double income = 0;
                    double expend = 0;
                    if (userdata.size() > 0) {
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("income".equals(entry.getKey())) {
                                income = (double) entry.getValue();
                            }
                            if ("expend".equals(entry.getKey())) {
                                expend = (double) entry.getValue();
                            }
                        }
                    }
                  /*  String incomes = s.substring(s.indexOf("userdata={income=") + 17, s.indexOf(", expend"));
                    String expends = s.substring(s.indexOf("expend=") + 7, s.indexOf(", serial_number="));*/
                    CunQu c = new CunQu();
                    c.setIncomes(income);
                    c.setExpengs(expend);
                    ccs.add(c);
                    cqs.add(ccs);
                /*    String Records = s.substring(s.indexOf("records=") + 8, s.indexOf(".0, rows="));
                    int i1 = Integer.parseInt(Records);
                    String substring = s.substring(s.indexOf("rows=[") + 6, s.indexOf("], userdata="));*/
                    List<CunQu> cs = new ArrayList<CunQu>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            Map<String, Object> o = (Map<String, Object>) rows.get(i);
                            Object id = 0;
                            List<Object> cell = new ArrayList<>();
                            if (o.size() > 0) {
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (Object) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<Object>) entry.getValue();
                                    }
                                }
                            }
                            CunQu cunQu = new CunQu();
                            if (id instanceof String) {
                                cunQu.setId(Integer.parseInt(String.valueOf(id)));
                            } else {
                                cunQu.setId(RxUtils.getInstance().getInt(id));
                            }

                            cunQu.setSerial_number((String) cell.get(0));
                            cunQu.setUname((String) cell.get(1));
                            cunQu.setDate((String) cell.get(2));
                            if (cell.get(3) instanceof String) {
                                cunQu.setStype(Integer.parseInt(String.valueOf(cell.get(3))));
                            } else {
                                cunQu.setStype(RxUtils.getInstance().getInt(cell.get(3)));
                            }

                            cunQu.setIncome(Double.parseDouble((String) cell.get(4)));
                            cunQu.setExpend(Double.parseDouble((String) cell.get(5)));
                            cunQu.setAmount(Double.parseDouble((String) cell.get(6)));
                            if (cell.get(7) instanceof String) {
                                cunQu.setStatus(Integer.parseInt(String.valueOf(cell.get(7))));
                            } else {
                                cunQu.setStatus(RxUtils.getInstance().getInt(cell.get(7)));
                            }

                            cunQu.setDetial((String) cell.get(8));
                            cs.add(cunQu);
                        }
                        cqs.add(cs);
                    }

                    listener.onReceivedData(API_ID_TEAMCQ, cqs, API_ID_ERROR);
                }


            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //查询团队报表彩票帐变
    public void getTeamAccountChangeList(final DataListener listener, int page, int rows, String sidx, String sord, String from, final String to, int id, String name, int type, int stype, int model) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", to + "");
        maps.put("to", from + "");
        maps.put("id", id + "");
        maps.put("name", name + "");
        maps.put("type", type + "");
        maps.put("stype", stype + "");
        maps.put("model", model + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> accountChangeList = apiInterface.getTeamAccountChangeList(1, page, rows, sidx, sord, to, from, id, name, type, stype, model, reqkey, currentTimeMillis);
        String s = accountChangeList.request().toString();
        Log.d("查询团队报表彩票帐变请求", s + "");
        Call<Map<String, Object>> clone = accountChangeList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("查询团队报表彩票帐变", s + "");
                    List<List<AccountChange>> account = new ArrayList<List<AccountChange>>();
                    Map<String, Object> map = response.body();
                    List<Object> rows = new ArrayList<>();
                    Map<String, Object> userdata = new HashMap<>();
                    double records = 0;
                    double amountAll = 0;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Object>) entry.getValue();
                            }
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    List<AccountChange> acs1 = new ArrayList<AccountChange>();
                    if (userdata.size() > 0) {
                        AccountChange ac = new AccountChange();
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("amount".equals(entry.getKey())) {
                                amountAll = (double) entry.getValue();
                                ac.setAmountss(amountAll);
                                acs1.add(ac);
                            }
                        }
                    }
                    account.add(acs1);
                    List<AccountChange> acs2 = new ArrayList<AccountChange>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            String id = null;
                            List<Object> cell = new ArrayList<>();
                            Map<String, Object> o = (Map<String, Object>) rows.get(i);
                            if (o.size() > 0) {
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (String) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<Object>) entry.getValue();
                                    }
                                }
                            }

                            for (int j = 0; j < cell.size(); j = j + 9) {
                                String uname = (String) cell.get(j);
                                String date = (String) cell.get(j + 1);
                                String stype = (String) cell.get(j + 2);
                                String gname = (String) cell.get(j + 3);
                                String rname = (String) cell.get(j + 4);
                                String period = (String) cell.get(j + 5);
                                String model = (String) cell.get(j + 6);
                                String amount = (String) cell.get(j + 7);
                                String amounts = (String) cell.get(j + 8);
                                AccountChange ac = new AccountChange();
                                ac.setId(Integer.parseInt(id));
                                ac.setUname(uname);
                                ac.setDate(date);
                                ac.setStype(Integer.parseInt(stype));
                                ac.setGname(gname);
                                ac.setRname(rname);
                                ac.setPeriod(period);
                                ac.setModel(Integer.parseInt(model));
                                ac.setAmount(Double.parseDouble(amount));
                                ac.setAmounts(Double.parseDouble(amounts));
                                acs2.add(ac);
                            }
                        }


                    }
                    account.add(acs2);
/* List<AccountChange> acs1 = new ArrayList<AccountChange>();
                    String substring = s.substring(s.indexOf("records=") + 8, s.indexOf(".0, rows="));
                    int ReCords = Integer.parseInt(substring);
                    String rows = s.substring(s.indexOf("rows=[") + 6, s.indexOf("], userdata="));
                    AccountChange a = new AccountChange();
                    String as = s.substring(s.indexOf("userdata={amount=") + 17, s.indexOf(", name="));
                    a.setAmountss(Double.parseDouble(as));
                    acs1.add(a);
                    account.add(acs1);
                    int length = rows.length();
                    if (ReCords > 0) {
                        String[] ss = rows.split(", ");
                        List<AccountChange> acs2 = new ArrayList<AccountChange>();
                        for (int i = 0; i < ss.length; i = i + 10) {
                            AccountChange ac = new AccountChange();
                            String id = ss[i].substring(4, ss[i].length());
                            String uname = ss[i + 1].substring(6, ss[i + 1].length());
                            String date = ss[i + 2].substring(0, ss[i + 2].length());
                            String stype = ss[i + 3];
                            String gname = ss[i + 4];
                            String rname = ss[i + 5];
                            String period = ss[i + 6];
                            String model = ss[i + 7];
                            String amount = ss[i + 8];
                            String amounts = ss[i + 9].substring(0, ss[i + 9].length() - 3);
                            ac.setId(Integer.parseInt(id));
                            ac.setUname(uname);
                            ac.setDate(date);
                            ac.setStype(Integer.parseInt(stype));
                            ac.setGname(gname);
                            ac.setRname(rname);
                            ac.setPeriod(period);
                            ac.setModel(Integer.parseInt(model));
                            ac.setAmount(Double.parseDouble(amount));
                            ac.setAmounts(Double.parseDouble(amounts));
                            acs2.add(ac);
                            //Log.d("查询个人报表彩票帐变SS", acs.toString());
                        }
                        account.add(acs2);

                    }*/
                    listener.onReceivedData(API_ID_TEAMACCOUNTCHANGE, account, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //新用户注册
    public void getSignUp(final DataListener listener, String u, String n, String p, String code, String c) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("u", u + "");
        map.put("n", n + "");
        map.put("p", p + "");
        map.put("code", code + "");
        map.put("c", c + "");
        String t = "";
        String reqkey = RxUtils.getInstance().getRegistersReqkey(map, t);
        String httpUrl = ApiInterface.HOST + "/signup?AppClient=1&u=" + u + "&n=" + n + "&p=" + p + "&code=" + code + "&c=" + c + "&t=" + t + "&reqkey=" + reqkey;
        OkGo.post(httpUrl)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        Gson gson = new Gson();
                        RestultInfo restultInfo = gson.fromJson(s, RestultInfo.class);
                        Log.d("注册返回信息验证", s);
                        listener.onReceivedData(API_ID_SIGNUP, restultInfo, API_ID_ERROR);
                    }

                    @Override
                    public void onError(okhttp3.Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.d("注册返回信息验证Error", e.toString());
                    }
                });

       /* Call<Object> signUp = apiInterface.getSignUp(1, u, n, p, code, c, reqkey, currentTimeMillis);
        Call<Object> clone = signUp.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });*/
    }

    //个人报表活动记录
    public void getActivityRecordList(final DataListener listener, int page, int rows, String sidx, String sord, String from, String to, int type) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("type", type + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> activityRecordList = apiInterface.getActivityRecordList(1, page, rows, sidx, sord, from, to, type, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = activityRecordList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("个人报表活动记录", response.body().toString());

                    Map<String, Object> map = response.body();
                    double records = 0;
                    List<Object> rows = new ArrayList<>();
                    Map<String, Object> userdata = new HashMap<>();
                    List<List<ActivityBean>> abs = new ArrayList<List<ActivityBean>>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Object>) entry.getValue();
                            }
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    double samount = 0;
                    List<ActivityBean> ab1 = new ArrayList<ActivityBean>();
                    if (userdata.size() > 0) {
                        ActivityBean a = new ActivityBean();
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("samount".equals(entry.getKey())) {
                                samount = (double) entry.getValue();
                            }
                        }
                        a.setSamount(samount);
                        ab1.add(a);

                    }
                    List<ActivityBean> ab2 = new ArrayList<ActivityBean>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            Map<String, Object> o = (Map<String, Object>) rows.get(i);
                            if (o.size() > 0) {
                                String id = null;
                                List<String> cell = new ArrayList<String>();
                                ActivityBean a = new ActivityBean();
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (String) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<String>) entry.getValue();
                                    }
                                }
                                for (int j = 0; j < cell.size(); j = j + 7) {
                                    String serial_number = cell.get(j);
                                    String uname = cell.get(j + 1);
                                    String date = cell.get(j + 2);
                                    String stype = cell.get(j + 3);
                                    String amount = cell.get(j + 4);
                                    String amounts = cell.get(j + 5);
                                    String detial = cell.get(j + 6);
                                    a.setId(Integer.parseInt(id));
                                    a.setSerial_number(serial_number);
                                    a.setUname(uname);
                                    a.setDate(date);
                                    a.setStype(Integer.parseInt(stype));
                                    a.setAmount(Double.parseDouble(amount));
                                    a.setAmounts(Double.parseDouble(amounts));
                                    a.setDetial(detial);
                                    Log.d("个人报表活动记录a", a.toString() + "");
                                }
                                ab2.add(a);
                            }
                        }
                    }
                    abs.add(ab1);
                    abs.add(ab2);
                    listener.onReceivedData(API_ID_MYACTIVITYLIST, abs, API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //团队报表活动记录
    public void getActivityTeamRecordList(final DataListener listener, int page, int rows, String sidx, String sord, String from, String to, String name, int type, int team) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("name", name + "");
        maps.put("type", type + "");
        maps.put("team", team + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> activityRecordList = apiInterface.getActivityTeamRecordList(1, page, rows, sidx, sord, from, to, name, type, team, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = activityRecordList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                int code = response.code();
                Log.d("团队报表活动记录Code", code + "");
                if (response.code() == 200) {
                    Log.d("团队报表活动记录", response.body().toString());

                    Map<String, Object> map = response.body();
                    double records = 0;
                    List<Object> rows = new ArrayList<>();
                    Map<String, Object> userdata = new HashMap<>();
                    List<List<ActivityBean>> abs = new ArrayList<List<ActivityBean>>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Object>) entry.getValue();
                            }
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    double samount = 0;
                    List<ActivityBean> ab1 = new ArrayList<ActivityBean>();
                    if (userdata.size() > 0) {
                        ActivityBean a = new ActivityBean();
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {
                            if ("samount".equals(entry.getKey())) {
                                samount = (double) entry.getValue();
                            }
                        }
                        a.setSamount(samount);
                        ab1.add(a);

                    }
                    List<ActivityBean> ab2 = new ArrayList<ActivityBean>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            Map<String, Object> o = (Map<String, Object>) rows.get(i);
                            if (o.size() > 0) {
                                String id = null;
                                List<String> cell = new ArrayList<String>();
                                ActivityBean a = new ActivityBean();
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (String) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<String>) entry.getValue();
                                    }
                                }
                                for (int j = 0; j < cell.size(); j = j + 7) {
                                    String serial_number = cell.get(j);
                                    String uname = cell.get(j + 1);
                                    String date = cell.get(j + 2);
                                    String stype = cell.get(j + 3);
                                    String amount = cell.get(j + 4);
                                    String amounts = cell.get(j + 5);
                                    String detial = cell.get(j + 6);
                                    a.setId(Integer.parseInt(id));
                                    a.setSerial_number(serial_number);
                                    a.setUname(uname);
                                    a.setDate(date);
                                    a.setStype(Integer.parseInt(stype));
                                    a.setAmount(Double.parseDouble(amount));
                                    a.setAmounts(Double.parseDouble(amounts));
                                    a.setDetial(detial);
                                    Log.d("团队报表活动记录a", a.toString() + "");
                                }
                                ab2.add(a);
                            }
                        }
                    }
                    abs.add(ab1);
                    abs.add(ab2);
                    listener.onReceivedData(API_ID_TEAMACTIVITYLIST, abs, API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    /*@Path("gid") int gid,
                @Query("AppClient") int num,
                @Query("vcode1") String vcode1,
                @Query("ids") List<Map<String, Object>> ids,
                @Query("period") String period,
                @Query("array") List<Map<String, Object>> array,
                @Query("amount") double amount,
                @Query("stopByWin") int stopByWin,
                @Query("reqkey") String reqkey,
                @Query("t") long t*/
    //提交购彩单
    public void getSendBetting(final DataListener listener, int gid, String vcode1, String ids, String period, String array, double amount, int stopByWin) {
        long currentThreadTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        map.put("vcode1", vcode1 + "");
        map.put("ids", ids + "");
        map.put("period", period + "");
        map.put("array", array + "");
        map.put("amount", amount + "");
        map.put("stopByWin", stopByWin + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentThreadTimeMillis);
        Call<RestultInfo> sendBetting = apiInterface.getSendBetting(gid, 1, amount, array, ids, period, reqkey, stopByWin, currentThreadTimeMillis, vcode1);
        String s = sendBetting.request().toString();
        Request request = sendBetting.request();
        final List<String> list = request.url().encodedPathSegments();

        Log.d("提交购彩单的请求整体", s);
        Call<RestultInfo> clone = sendBetting.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                int code = response.code();
                Log.d("提交购彩单Code==", code + "");
                if (code == 200) {
                    Log.d("提交购彩单", response.body().toString());
                    listener.onReceivedData(API_ID_SENDBETTING, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Log.d("提交购彩单Error", t.toString());
            }
        });

    }

    //切换游戏/获取玩法数据(重点)
    public void getSwitchGameList(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> switchGameList = apiInterface.getSwitchGameList(id, 1, reqkey, currentTimeMillis);
        Log.d("切换游戏/获取玩法数据(重点)String=", switchGameList.request().toString() + "");
        String s = switchGameList.request().toString();
        String s1 = s.substring(s.indexOf("-game/") + 6, s.indexOf("?"));
        Log.d("切换游戏/获取玩法数据(重点)s1=", s1 + "");
        Call<Map<String, Object>> clone = switchGameList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                int code = response.code();
                Log.d("切换游戏/获取玩法数据(重点)Code=", code + "");
                if (code == 200) {
                    Log.d("RES切换游戏/获取玩法数据(重点)", response.body().toString());
                    Map<String, Object> map = response.body();
                    double gid = 0;//游戏id
                    String status = null;//游戏状态，不为1则游戏已暂停销售
                    String gameName = null;//游戏名称
                    Map<String, Object> zodiacs = new HashMap<>();//生肖号码数据，仅当gid=28游戏为六合彩时，才返回该数据
                    double rebate = 0;//用户游戏返点，用于计算最高奖和显示最低奖投注返点
                    double serverTime = 0;//服务器当前时间时间戳，用于倒计时校准等
                    Map<String, Object> datas = new HashMap<>();//玩法数据
                    String defaultGameRule = null;//默认玩法编码
                    String howto = null;//默认玩法说明标签
                    String example = null;//默认玩法[投注示例|开奖示例]
                    String desc = null;//默认玩法说明
                    double pricetype = 0;//默认奖金类型
                    List<Object> priceTypes = new ArrayList<>();//默认玩法等级间隔系数，最低奖
                    String property = null;//开奖号码性质
                    String prePeriod = null;//开奖号码期号(上期开奖期号)
                    String winningNumber = null;//逗号分隔的开奖号码
                    String amount = null;//账户余额
                    String period = null;//当前期号
                    double count = 0;//未开奖期数
                    SwitchGame switchGame;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("priceTypes".equals(entry.getKey())) {
                                priceTypes = (List<Object>) entry.getValue();
                            }
                            if ("gid".equals(entry.getKey())) {
                                gid = (double) entry.getValue();
                            }
                            if ("status".equals(entry.getKey())) {
                                status = (String) entry.getValue();
                            }
                            if ("gameName".equals(entry.getKey())) {
                                gameName = (String) entry.getValue();
                            }
                            if ("zodiacs".equals(entry.getKey())) {
                                zodiacs = (Map<String, Object>) entry.getValue();
                            }
                            if ("rebate".equals(entry.getKey())) {
                                rebate = (double) entry.getValue();
                            }
                            if ("serverTime".equals(entry.getKey())) {
                                serverTime = (double) entry.getValue();
                            }
                            if ("datas".equals(entry.getKey())) {
                                datas = (Map<String, Object>) entry.getValue();
                            }
                            if ("defaultGameRule".equals(entry.getKey())) {
                                defaultGameRule = (String) entry.getValue();
                            }
                            if ("howto".equals(entry.getKey())) {
                                howto = (String) entry.getValue();
                            }
                            if ("example".equals(entry.getKey())) {
                                example = (String) entry.getValue();
                            }
                            if ("desc".equals(entry.getKey())) {
                                desc = (String) entry.getValue();
                            }
                            if ("pricetype".equals(entry.getKey())) {
                                pricetype = (double) entry.getValue();
                            }
                            if ("priceTypes".equals(entry.getKey())) {
                                priceTypes = (List<Object>) entry.getValue();
                            }
                            if ("property".equals(entry.getKey())) {
                                property = (String) entry.getValue();
                            }
                            if ("prePeriod".equals(entry.getKey())) {
                                prePeriod = (String) entry.getValue();
                            }
                            if ("winningNumber".equals(entry.getKey())) {
                                winningNumber = (String) entry.getValue();
                            }
                            if ("amount".equals(entry.getKey())) {
                                amount = (String) entry.getValue();
                            }
                            if ("period".equals(entry.getKey())) {
                                period = (String) entry.getValue();
                            }
                            if ("count".equals(entry.getKey())) {
                                count = (double) entry.getValue();
                            }
                        }
                    }
                    switchGame = new SwitchGame();
                    switchGame.setGid(RxUtils.getInstance().getInt(gid));
                    switchGame.setStatus(Integer.parseInt(status));
                    switchGame.setGameName(gameName);
                    switchGame.setRebate(rebate);
                    switchGame.setServerTime(serverTime);
                    switchGame.setDefaultGameRule(defaultGameRule);
                    switchGame.setHowto(howto);
                    switchGame.setExample(example);
                    switchGame.setDesc(desc);
                    switchGame.setPricetype(RxUtils.getInstance().getInt(pricetype));
                    switchGame.setProperty(property);
                    switchGame.setPrePeriod(prePeriod);
                    switchGame.setWinningNumber(winningNumber);
                    switchGame.setAmount(Double.parseDouble(amount));
                    switchGame.setPeriod(period);
                    switchGame.setCount(RxUtils.getInstance().getInt(count));
                    Log.d("切换游戏/获取玩法数据(重点)String", switchGame.toString());
                    double rate = 0;
                    List<Object> list = new ArrayList<>();
                    //   List<List<List<SwitchGame>>> s = new ArrayList<List<List<SwitchGame>>>();
                    List<SwitchG> sg = new ArrayList<SwitchG>();
                    if (datas.size() > 0) {
                        for (Map.Entry<String, Object> entry : datas.entrySet()) {
                            if ("rate".equals(entry.getKey())) {
                                rate = (double) entry.getValue();
                            }
                            if ("list".equals(entry.getKey())) {
                                list = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    /*List<SwitchGame> s1 = new ArrayList<SwitchGame>();
                    List<SwitchGame> s2 = new ArrayList<SwitchGame>();
                    List<SwitchGame> s3 = new ArrayList<SwitchGame>();*/
                    for (int j = 0; j < list.size(); j++) {
                        List<Object> o = (List<Object>) list.get(j);
                        String id = (String) o.get(0);
                        String nameOne = (String) o.get(1);

                        Log.d("RES第一层", id + "   " + nameOne);
                        SwitchG s = new SwitchG();
                        s.setId1(id);
                        s.setName1(nameOne);
                        s.setRate(rate);
                        if (!"null".equals(o.get(2))) {
                            List<Object> o1 = new ArrayList<>();
                            if (o.get(2) == null) {
                                o1 = (List<Object>) o.get(3);
                            } else {
                                o1 = (List<Object>) o.get(2);
                            }
                            List<SwitchG.SwitchGa> ssg = new ArrayList<SwitchG.SwitchGa>();

                            for (int k = 0; k < o1.size(); k++) {
                                List<Object> list2 = (List) o1.get(k);
                                Log.d("RES第二层List", list2.toString() + "  XXX");
                                Object id2 = (Object) list2.get(0);
                                Object name2 = (Object) list2.get(1);
                                SwitchG.SwitchGa ssa = new SwitchG.SwitchGa();
                                ssa.setId2(id2);
                                ssa.setName2(name2);
                                String clacode = "";
                                if (list2.get(2) instanceof String) {
                                    clacode = (String) list2.get(2);
                                    ssa.setClass_code2(clacode);
                                }
                                if (list2.size() > 3) {
                                    double coefficient22 = (double) list2.get(3);
                                    double min22 = (double) list2.get(4);
                                    String description2 = (String) list2.get(8);
                                    String grprize2 = (String) list2.get(9);
                                    Log.d("RES第二层11", id2 + "   " + name2 + "   " + coefficient22 + "   " + min22 + "   " + description2 + "   " + grprize2);
                                    ssa.setMinimum2(min22);
                                    ssa.setCoefficient2(coefficient22);
                                    ssa.setRate2(rate);
                                    ssa.setDescription2(description2);
                                    ssa.setGrprize2(grprize2);
                                }

                                ssg.add(ssa);

                                List<Object> list3 = new ArrayList<>();
                                if (list2.get(2) instanceof List) {
                                    list3 = (List<Object>) list2.get(2);
                                }
                                List<SwitchG.SwitchGa.SwitchGam> sga = new ArrayList<SwitchG.SwitchGa.SwitchGam>();
                                for (int l = 0; l < list3.size(); l++) {
                                    List ll = (List) list3.get(l);
                                    Object id3 = (Object) ll.get(0);
                                    Object name3 = (Object) ll.get(1);
                                    String class_code3 = (String) ll.get(2);
                                    double coefficient = (double) ll.get(3);
                                    double min = (double) ll.get(4);
                                    String description3 = (String) ll.get(8);
                                    String grprize3 = (String) ll.get(9);
                                    Log.d("RES第三层", id3 + "   " + name3 + "   " + coefficient + "   " + min + "   " + description3 + "   " + grprize3);

                                    SwitchG.SwitchGa.SwitchGam sssg = new SwitchG.SwitchGa.SwitchGam();
                                    sssg.setId3(id3);
                                    sssg.setName3(name3);
                                    sssg.setClass_code3(class_code3);
                                    sssg.setMinimum3(min);
                                    sssg.setCoefficient3(coefficient);
                                    sssg.setRate3(rate);
                                    sssg.setDescription3(description3);
                                    sssg.setGrprize3(grprize3);
                                    sga.add(sssg);


                                }
                                ssa.setSwitchGams(sga);
                                s.setSwitchGas(ssg);
                            }
                        } else {
                            List<Object> o1 = (List<Object>) o.get(3);
                            for (int k = 0; k < o1.size(); k++) {
                                List<Object> list2 = (List) o1.get(k);
                                Object id2 = (Object) list2.get(0);
                                Object name2 = (Object) list2.get(1);
                                String class_code2 = (String) list2.get(2);
                                double coefficient = (double) list.get(3);
                                double min = (double) list.get(4);
                                String description4 = (String) list.get(8);
                                String grprize4 = (String) list.get(9);
                                Log.d("RE第二层22", id2 + "   " + name2 + "   " + coefficient + "   " + min + "   " + description4 + "   " + grprize4);
                                List<SwitchG.SwitchGa> ssg = new ArrayList<SwitchG.SwitchGa>();
                                SwitchG.SwitchGa ssa = new SwitchG.SwitchGa();
                                ssa.setId2(id2);
                                ssa.setName2(name2);
                                ssa.setClass_code2(class_code2);
                                ssa.setMinimum2(min);
                                ssa.setCoefficient2(coefficient);
                                ssa.setRate2(rate);
                                ssa.setDescription2(description4);
                                ssa.setGrprize2(grprize4);
                                ssg.add(ssa);
                            }
                        }
                        sg.add(s);
                    }
                    for (int i = 0; i < sg.size(); i++) {
                        SwitchG switchG = sg.get(i);
                        Log.d("RESSS第一层", sg.get(i).toString());
                        for (int i1 = 0; i1 < switchG.getSwitchGas().size(); i1++) {
                            Log.d("RESSS第二层", switchG.getSwitchGas().get(i1).toString());
                            List<SwitchG.SwitchGa.SwitchGam> switchGams = switchG.getSwitchGas().get(i1).getSwitchGams();
                            if (switchGams != null) {
                                for (int i2 = 0; i2 < switchGams.size(); i2++) {
                                    Log.d("RESSS第三层", switchGams.get(i2).toString());
                                }
                            }
                        }


                    }
                    listener.onReceivedData(API_ID_SWITCHGAME, sg, API_ID_ERROR);
                    listener.onReceivedData(API_ID_SWITCHGAME_STATUS, switchGame, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //同步倒计时时间
    public void getBettingSync(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> bettingSync = apiInterface.getBettingSync(1, id, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = bettingSync.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String msg = response.body().toString();
                    Log.d("同步倒计时时间", msg);
                    Map<String, Object> map = response.body();
                    boolean rc = false;
                    double id = 0;
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("id".equals(entry.getKey())) {
                                id = (double) entry.getValue();
                            }
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    BettingSync bettingSync = new BettingSync();
                    if (rc) {
                        if (others.size() > 0) {
                            double condownTime = 0;
                            double gid = 0;
                            String period = null;
                            String drawTime = null;
                            double count = 0;
                            for (Map.Entry<String, Object> entry : others.entrySet()) {
                                if ("condownTime".equals(entry.getKey())) {
                                    condownTime = (double) entry.getValue();
                                }
                                if ("gid".equals(entry.getKey())) {
                                    gid = (double) entry.getValue();
                                }
                                if ("period".equals(entry.getKey())) {
                                    period = (String) entry.getValue();
                                }
                                if ("drawTime".equals(entry.getKey())) {
                                    drawTime = (String) entry.getValue();
                                }
                                if ("count".equals(entry.getKey())) {
                                    count = (double) entry.getValue();
                                }
                            }

                            bettingSync.setCondownTime((long) condownTime);
                            bettingSync.setPeriod(period);
                            bettingSync.setGid(RxUtils.getInstance().getInt(gid));
                            bettingSync.setDrawTime(Long.parseLong(drawTime));
                            bettingSync.setCount(RxUtils.getInstance().getInt(count));
                        }
                    }
                    listener.onReceivedData(API_ID_BETTINGSYNC, bettingSync, API_ID_ERROR);


                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //88更新返点
    public void getBettingUpdateRate(DataListener listener, int gid, double rate) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("gid", gid + "");
        map.put("rate", rate + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> bettingUpdateRate = apiInterface.getBettingUpdateRate(1, gid, rate, reqkey, currentTimeMillis);
        Call<Object> clone = bettingUpdateRate.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("88更新返点", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    //获取开奖历史记录
    public void getBettingDrawHistory(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<List<Object>> clone = apiInterface.getBettingDrawHistory(id, 1, reqkey, currentTimeMillis);
        Log.d("获取开奖历史记录请求完全体", clone.request().toString() + "");
        Call<List<Object>> clone1 = clone.clone();

        clone1.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, retrofit2.Response<List<Object>> response) {
                Log.d("获取开奖历史记录Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("获取开奖历史记录1", response.body().toString());
                    List<Object> list = response.body();
                    List<RecordList> rcs = new ArrayList<RecordList>();
                    for (int i = 0; i < list.size(); i++) {
                        List<String> o = (List<String>) list.get(i);
                        RecordList rc = new RecordList();
                        rc.setPeriod(o.get(0));
                        rc.setWinningNumber(o.get(1));
                        rcs.add(rc);
                    }
                    listener.onReceivedData(API_ID_HISTORYGAME, rcs, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {

            }
        });


    }

    //获取推广设置数据
    public void getShareData(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> shareData = apiInterface.getShareData(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = shareData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("获取推广设置数据", response.body().toString());
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();

                    List<List<ShareData>> sds = new ArrayList<List<ShareData>>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }

                    List<ShareData> sd = new ArrayList<ShareData>();

                    sds.add(sd);
                    List<Object> glist = new ArrayList<>();
                    if (others.size() > 0) {
                        double uid = 0;
                        double rebate_id = 0;
                        double rate = 0;
                        double mxrate = 0;
                        boolean checked = false;
                        String shareCode = "";
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if ("uid".equals(entry.getKey())) {
                                uid = (double) entry.getValue();
                            }
                            if ("checked".equals(entry.getKey())) {
                                checked = (boolean) entry.getValue();
                            }
                            if ("rebate_id".equals(entry.getKey())) {
                                rebate_id = (double) entry.getValue();
                            }
                            if ("rate".equals(entry.getKey())) {
                                rate = (double) entry.getValue();
                            }
                            if ("mxrate".equals(entry.getKey())) {
                                mxrate = (double) entry.getValue();
                            }
                            if ("glist".equals(entry.getKey())) {
                                glist = (List<Object>) entry.getValue();
                            }
                            if ("shareCode".equals(entry.getKey())) {
                                shareCode = (String) entry.getValue();
                            }
                        }
                        ShareData sd2 = new ShareData();
                        sd2.setChecked(checked);
                        sd2.setUid(RxUtils.getInstance().getInt(uid));
                        sd2.setRebate_id(RxUtils.getInstance().getInt(rebate_id));
                        sd2.setRate(RxUtils.getInstance().getInt(rate));
                        sd2.setMxrate(RxUtils.getInstance().getInt(mxrate));
                        sd2.setShareCode(shareCode);
                        sd.add(sd2);

                    }
                    List<ShareData> sd1 = new ArrayList<ShareData>();
                    for (int i = 0; i < glist.size(); i++) {
                        List<Object> o = (List<Object>) glist.get(i);
                        for (int j = 0; j < o.size(); j = j + 3) {
                            String name = (String) o.get(j);
                            double grate = (double) o.get(j + 1);
                            double offset = (double) o.get(j + 2);
                            ShareData s = new ShareData();
                            s.setName(name);
                            s.setGrate(grate);
                            s.setOffset(offset);
                            sd1.add(s);
                        }
                    }
                    sds.add(sd1);
                    listener.onReceivedData(API_ID_SHAREDATA, sds, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }  //推广设置-保存

    public void getGeneralizeSave(final DataListener listener, double l, int t) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        map.put("l", l + "");
        String reqkey = RxUtils.getInstance().getRegistersReqkey(map, t);
        Call<RestultInfo> shareData = apiInterface.getGeneralizeSave(1, l, reqkey, t);
        Call<RestultInfo> clone = shareData.clone();
        Log.d("推广设置-保存请求完全体", clone.request().toString() + "");
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("推广设置-保存", response.body().toString());
                listener.onReceivedData(API_ID_GENERALIZESAVE, response.body(), API_ID_ERROR);

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Log.d("推广设置-保存Error", t.toString());
            }
        });
    }

    //获取添加会员数据
    public void getAddUserData(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map map = new HashMap();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> shareData = apiInterface.getAddUserData(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = shareData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取添加会员数据", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> rates = new HashMap<>();
                    List<Object> AddNewDatas = new ArrayList<>();
                    Map<String, Object> others = new HashMap<>();
                    String code = null;
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    for (Map.Entry<String, Object> entry : others.entrySet()) {
                        if ("code".equals(entry.getKey())) {
                            code = (String) entry.getValue();
                        }
                        if ("rates".equals(entry.getKey())) {
                            rates = (Map<String, Object>) entry.getValue();
                        }
                    }

                    AddNewDatas.add(code);
                    Log.d("ShowRates", rates.size() + "");
                    AddNewDatas.add(rates);

                    listener.onReceivedData(API_ID_ADDVIPCODE, AddNewDatas, API_ID_ERROR);

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //Token自动登录
    public void getTokenSignin(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, String>> tokenSignin = apiInterface.getTokenSignin(1, reqkey, currentTimeMillis);
        Call<Map<String, String>> clone = tokenSignin.clone();
        clone.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, retrofit2.Response<Map<String, String>> response) {
                if (response.code() == 200) {
                    Log.d("Token自动登录", response.body().toString());
                    Map<String, String> map = new HashMap<>();
                    map = response.body();
                    boolean state = false;
                    String message = "";
                    if (map.size() > 0) {
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (entry.getKey().equals("state")) {
                                state = Boolean.parseBoolean(entry.getValue());
                            }
                            if (entry.getKey().equals("message")) {
                                message = entry.getValue();
                            }

                        }
                    }
                    RestultInfo restultInfo = new RestultInfo();
                    restultInfo.setState(state);
                    restultInfo.setMessage(message);
                    if ("true".equals(state)) {
                        restultInfo.setState(true);
                    } else {
                        restultInfo.setState(false);
                    }
                    restultInfo.setMessage(message);
                    listener.onReceivedData(API_ID_TOKENLOGIN, restultInfo, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }

    //团队报表彩票投注
    public void getTeamBettingList(final DataListener listener, int page, int rows, String sidx, String sord, String from, String to, String name, int status, int id, int rid, int type) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("name", name + "");
        maps.put("status", status + "");
        maps.put("id", id + "");
        maps.put("rid", rid + "");
        maps.put("type", type + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> teamBettingList = apiInterface.getTeamBettingList(1, page, rows, sidx, sord, from, to, name, status, id, rid, type, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = teamBettingList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("团队报表彩票投注Content", s);
                    double totalElements = 0;
                    List<Object> content = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : response.body().entrySet()) {
                        if ("totalElements".equals(entry.getKey())) {
                            totalElements = (double) entry.getValue();
                        }
                        if ("content".equals(entry.getKey())) {
                            content = (List<Object>) entry.getValue();
                        }
                    }
                    List<UserTeamBetting> userBetting = new ArrayList<UserTeamBetting>();
                    if (totalElements > 0) {
                        for (int i = 0; i < content.size(); i++) {
                            List<Object> cn = (List<Object>) content.get(i);
                            UserTeamBetting us = new UserTeamBetting();
                            us.setBid(RxUtils.getInstance().getInt(cn.get(0)));
                            us.setUid(RxUtils.getInstance().getInt(cn.get(1)));
                            us.setUname((String) cn.get(2));
                            us.setDate((String) cn.get(3));
                            us.setGname((String) cn.get(4));
                            us.setPeriod((String) cn.get(5));
                            us.setRname((String) cn.get(6));
                            us.setPicked_text((String) cn.get(7));
                            us.setAmount((Double) cn.get(8));

                            if (cn.get(9) == null) {
                                us.setPrize(0.0);
                            } else {
                                us.setPrize((Double) cn.get(9));
                            }

                            us.setWinning_number((String) cn.get(10));
                            Log.d("团队报表彩票投注Status", RxUtils.getInstance().getInt(cn.get(11)) + "");
                            us.setStatus(RxUtils.getInstance().getInt(cn.get(11)));
                            userBetting.add(us);
                        }
                       /* String con = s.substring(s.indexOf("content=[") + 9, s.indexOf("], number="));
                        String[] cs = con.split(", ");

                        for (int i = 0; i < cs.length; i = i + 12) {
                            UserTeamBetting us = new UserTeamBetting();
                            String bid = cs[i].substring(1, cs[i].length() - 2);
                            String uid = cs[i + 1].substring(0, cs[i + 1].length() - 2);
                            String uname = cs[i + 2];
                            String date = cs[i + 3];
                            String gname = cs[i + 4];
                            String period = cs[i + 5];
                            String rname = cs[i + 6];
                            String picked_text = cs[i + 7];
                            String amount = cs[i + 8];
                            String prize = cs[i + 9];
                            String winning_numbers = cs[i + 10];
                            String status = cs[i + 11].substring(0, cs[i + 11].length() - 3);
                            Log.d("彩票报表Split", cs[i]);
                            us.setBid(Integer.parseInt(bid));
                            us.setUid(Integer.parseInt(uid));
                            us.setUname(uname);
                            us.setDate(date);
                            us.setGname(gname);
                            us.setPeriod(period);
                            us.setRname(rname);
                            us.setPicked_text(picked_text);
                            us.setAmount(Double.parseDouble(amount));
                            us.setPrize(Double.parseDouble(prize));
                            us.setWinning_number(winning_numbers);
                            us.setStatus(Integer.parseInt(status));
                            userBetting.add(us);
                        }*/
                    }
                    listener.onReceivedData(API_ID_TEAMBETTING, userBetting, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("团队报表彩票投注Error", t.toString());
            }
        });

    }

    //团队报表当日盈亏
    public void getTeamProfitLossList(final DataListener listener, int page, int rows, String sidx, String sord, String from, String to, String name, int uid, int gtype, int stype, int team) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> maps = new HashMap<>();
        maps.put("page", page + "");
        maps.put("rows", rows + "");
        maps.put("sidx", sidx + "");
        maps.put("sord", sord + "");
        maps.put("from", from + "");
        maps.put("to", to + "");
        maps.put("name", name + "");
        maps.put("uid", uid + "");
        maps.put("gtype", gtype + "");
        maps.put("stype", stype + "");
        maps.put("team", team + "");
        String reqkey = RxUtils.getInstance().getReqkey(maps, currentTimeMillis);
        Call<Map<String, Object>> teamProfitLossList = apiInterface.getTeamProfitLossList(1, page, rows, sidx, sord, from, to, name, uid, gtype, stype, team, reqkey, currentTimeMillis);
        String s = teamProfitLossList.request().toString();
        Log.d("团队报表当日或历史盈亏请求", s);
        Call<Map<String, Object>> clone = teamProfitLossList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("团队报表当日或历史盈亏", response.body().toString());
                    Map<String, Object> map = response.body();
                    double records = 0;
                    List<Object> rows = new ArrayList<>();
                    Map<String, Object> userdata = new HashMap<>();
                    List<List<LotteryLoss>> loss = new ArrayList<List<LotteryLoss>>();
                    List<LotteryLoss> los1 = new ArrayList<LotteryLoss>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("records".equals(entry.getKey())) {
                                records = (double) entry.getValue();
                            }
                            if ("rows".equals(entry.getKey())) {
                                rows = (List<Object>) entry.getValue();
                            }
                            if ("userdata".equals(entry.getKey())) {
                                userdata = (Map<String, Object>) entry.getValue();
                            }
                        }

                    }


                    if (userdata.size() > 0) {
                        double activity_amount = 0;
                        double activity_amount2 = 0;
                        double recharge_amount = 0;
                        double withdraw_amount = 0;
                        double rebate_amount = 0;
                        double profit_loss = 0;
                        double betting_amount = 0;
                        double winning_amount = 0;
                        for (Map.Entry<String, Object> entry : userdata.entrySet()) {

                            if ("activity_amount".equals(entry.getKey())) {
                                activity_amount = (double) entry.getValue();
                            }
                            if ("activity_amount2".equals(entry.getKey())) {
                                activity_amount2 = (double) entry.getValue();
                            }
                            if ("recharge_amount".equals(entry.getKey())) {
                                recharge_amount = (double) entry.getValue();
                            }
                            if ("withdraw_amount".equals(entry.getKey())) {
                                withdraw_amount = (double) entry.getValue();
                            }
                            if ("rebate_amount".equals(entry.getKey())) {
                                rebate_amount = (double) entry.getValue();
                            }
                            if ("profit_loss".equals(entry.getKey())) {
                                profit_loss = (double) entry.getValue();
                            }
                            if ("betting_amount".equals(entry.getKey())) {
                                betting_amount = (double) entry.getValue();
                            }
                            if ("winning_amount".equals(entry.getKey())) {
                                winning_amount = (double) entry.getValue();
                            }

                        }
                        LotteryLoss l = new LotteryLoss();
                        l.setActivity_amount(activity_amount);
                        l.setActivity_amount2(activity_amount2);
                        l.setRecharge_amount(recharge_amount);
                        l.setWithdraw_amount(withdraw_amount);
                        l.setRebate_amount(rebate_amount);
                        l.setProfit_loss(profit_loss);
                        l.setBetting_amount(betting_amount);
                        l.setWinning_amount(winning_amount);
                        Log.d("团队报表当日或历史盈亏String", l.toString());
                        los1.add(l);
                    }

                    loss.add(los1);
                    List<LotteryLoss> ls2 = new ArrayList<LotteryLoss>();
                    if (records > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            Map<String, Object> o = (Map<String, Object>) rows.get(i);
                            String id = null;
                            List<Object> cell = new ArrayList<>();
                            if (o.size() > 0) {
                                for (Map.Entry<String, Object> entry : o.entrySet()) {
                                    if ("id".equals(entry.getKey())) {
                                        id = (String) entry.getValue();
                                    }
                                    if ("cell".equals(entry.getKey())) {
                                        cell = (List<Object>) entry.getValue();
                                    }
                                }
                            }
                            if (cell.size() > 0) {
                                for (int j = 0; j < cell.size(); j = j + 11) {
                                    String uname = (String) cell.get(i);
                                    String recharge_amount = (String) cell.get(i + 1);
                                    String withdraw_amount = (String) cell.get(i + 2);
                                    String betting_amount = (String) cell.get(i + 3);
                                    String rebate_amount = (String) cell.get(i + 4);
                                    String winning_amount = (String) cell.get(i + 5);
                                    String activity_amount = (String) cell.get(i + 6);
                                    String activity_amount2 = (String) cell.get(i + 7);
                                    String profit_loss = (String) cell.get(i + 8);
                                    String type = (String) cell.get(i + 9);
                                    String counts = (String) cell.get(i + 10);
                                    LotteryLoss ls = new LotteryLoss();
                                    ls.setId(Integer.parseInt(id));
                                    ls.setUname(uname);
                                    ls.setRecharge_amount(Double.parseDouble(recharge_amount));
                                    ls.setWithdraw_amount(Double.parseDouble(withdraw_amount));
                                    ls.setBetting_amount(Double.parseDouble(betting_amount));
                                    ls.setRebate_amount(Double.parseDouble(rebate_amount));
                                    ls.setWinning_amount(Double.parseDouble(winning_amount));
                                    ls.setActivity_amount(Double.parseDouble(activity_amount));
                                    ls.setActivity_amount2(Double.parseDouble(activity_amount2));
                                    ls.setProfit_loss(Double.parseDouble(profit_loss));
                                    ls.setType(Integer.parseInt(type));
                                    ls.setCounts(Integer.parseInt(counts));
                                    ls2.add(ls);
                                }
                            }
                        }
                    }
                    loss.add(ls2);
                    listener.onReceivedData(API_ID_LOTTERYLOSS, loss, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //会员统计
    public void getUserStatistics(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> userStatistics = apiInterface.getUserStatistics(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = userStatistics.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("会员统计", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    double headcount = 0;
                    double onlinecount = 0;
                    double amounts = 0;
                    double months = 0;
                    double todays = 0;
                    if (others.size() > 0) {
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if ("headcount".equals(entry.getKey())) {
                                headcount = (double) entry.getValue();
                            }
                            if ("onlinecount".equals(entry.getKey())) {
                                onlinecount = (double) entry.getValue();
                            }
                            if ("amounts".equals(entry.getKey())) {
                                amounts = (double) entry.getValue();
                            }
                            if ("months".equals(entry.getKey())) {
                                months = (double) entry.getValue();
                            }
                            if ("todays".equals(entry.getKey())) {
                                todays = (double) entry.getValue();
                            }
                        }
                    }
                    List<String> UserSta = new ArrayList<String>();
                    UserSta.add(headcount + "");
                    UserSta.add(onlinecount + "");
                    UserSta.add(amounts + "");
                    UserSta.add(months + "");
                    UserSta.add(todays + "");
                    listener.onReceivedData(API_ID_USERSTA, UserSta, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //查询统计的用户信息
    public void getTeamUserList(final DataListener listener, int page, int rows, String sidx, String sord, int type, int stype) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("type", type + "");
        map.put("stype", stype + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> teamUserList = apiInterface.getTeamUserList(1, page, rows, sidx, sord, type, stype, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = teamUserList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {

                if (response.code() == 200) {
                    List<List<TeamUserInfo>> ts = new ArrayList<List<TeamUserInfo>>();
                    String s = response.body().toString();
                    double totalElements = 0;
                    Log.d("查询统计的用户信息", s);
                    Map<String, Object> map = response.body();
                    List<Object> content = new ArrayList<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("totalElements".equals(entry.getKey())) {
                                totalElements = (double) entry.getValue();
                            }
                            if ("content".equals(entry.getKey())) {
                                content = (List<Object>) entry.getValue();
                            }
                        }
                    }

                    List<TeamUserInfo> t = new ArrayList<TeamUserInfo>();
                    TeamUserInfo teamUserInfo1 = new TeamUserInfo();
                    String s1 = totalElements + "";
                    String s2 = s1.substring(0, s1.length() - 2);
                    teamUserInfo1.setTotalElements(Integer.parseInt(s2));
                    t.add(teamUserInfo1);
                    ts.add(t);
                    if (totalElements > 0) {
                        List<TeamUserInfo> t1 = new ArrayList<TeamUserInfo>();
                        for (int i = 0; i < content.size(); i++) {
                            List<Object> li = (List<Object>) content.get(i);
                            TeamUserInfo team = new TeamUserInfo();
                            team.setUid(RxUtils.getInstance().getInt(li.get(0)));
                            team.setMine(RxUtils.getInstance().getInt(li.get(1)));
                            team.setUtype(RxUtils.getInstance().getInt(li.get(2)));
                            team.setName((String) li.get(3));
                            team.setAmount((Double) li.get(4));
                            team.setRebate_id(RxUtils.getInstance().getInt(li.get(5)));
                            team.setRate((Double) li.get(6));
                            team.setCreated((String) li.get(7));
                            team.setLogin((String) li.get(8));
                            team.setRtype(RxUtils.getInstance().getInt(li.get(9)));
                            team.setStatus(RxUtils.getInstance().getInt(li.get(10)));
                            team.setZu(RxUtils.getInstance().getInt(li.get(11)));
                            t1.add(team);
                        }
                       /* String Content = s.substring(s.indexOf("content=[") + 9, s.indexOf("], number="));
                        String[] cs = Content.split(", ");
                        for (int i = 0; i < cs.length; i = i + 12) {
                            Log.d("会员管理Split", cs[i]);
                            TeamUserInfo team = new TeamUserInfo();
                            String uid = cs[i].substring(1, cs[i].length() - 2);
                            String mine = cs[i + 1].substring(0, cs[i + 1].length() - 2);
                            String utype = cs[i + 2].substring(0, cs[i + 2].length() - 2);
                            String name = cs[i + 3];
                            String amount = cs[i + 4];
                            String rebate_id = cs[i + 5].substring(0, cs[i + 5].length() - 2);
                            String rate = cs[i + 6];
                            String created = cs[i + 7];
                            String login = cs[i + 8];
                            String rtype = cs[i + 9].substring(0, cs[i + 9].length() - 2);
                            String status = cs[i + 10].substring(0, cs[i + 10].length() - 2);
                            String zu = cs[i + 11].substring(0, cs[i + 11].length() - 3);
                            team.setUid(Integer.parseInt(uid));
                            team.setMine(Integer.parseInt(mine));
                            team.setUtype(Integer.parseInt(utype));
                            team.setName(name);
                            team.setAmount(Double.parseDouble(amount));
                            team.setRebate_id(Integer.parseInt(rebate_id));
                            team.setRate(Double.parseDouble(rate));
                            team.setCreated(created);
                            team.setLogin(login);
                            team.setRtype(Integer.parseInt(rtype));
                            team.setStatus(Integer.parseInt(status));
                            team.setZu(Integer.parseInt(zu));
                            t1.add(team);
                        }*/
                        ts.add(t1);
                    }
                    listener.onReceivedData(API_ID_TEAMUSERLIST, ts, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    //投注单 详情
    public void getBettingDetails(final DataListener listener, int id) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> bettingDetails = apiInterface.getBettingDetails(id, 1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = bettingDetails.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("投注单详情1", response.body().toString());
                    Map<String, Object> map = response.body();
                    List<Object> o = new ArrayList<>();
                    double id = 0;
                    boolean rc = false;
                    BettingDetail bd = new BettingDetail();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("id".equals(entry.getKey())) {
                                id = (double) entry.getValue();
                            }
                            if ("others".equals(entry.getKey())) {
                                o = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    for (int i = 0; i < o.size(); i++) {
                        bd.setRc(rc);
                        bd.setId(RxUtils.getInstance().getInt(id));
                        bd.setSerial_number((String) o.get(0));
                        bd.setUname((String) o.get(1));
                        bd.setBuy_time((String) o.get(2));
                        bd.setGname((String) o.get(3));
                        bd.setRname((String) o.get(4));
                        bd.setPeriod((String) o.get(5));
                        bd.setPicked_text((String) o.get(6));
                        bd.setMultiple(RxUtils.getInstance().getInt(o.get(7)));
                        bd.setPrice_unit(RxUtils.getInstance().getInt(o.get(8)));
                        bd.setAmount((Double) (o.get(9)));
                        if (o.get(10) != null) {
                            bd.setPrize((Double) (o.get(10)));
                        }
                        if (o.get(11) != null) {
                            bd.setWinning_numbers((String) (o.get(11)));
                        }
                        //bd.setStatus(RxUtils.getInstance().getInt(o.get(12)));
                        bd.setBid(RxUtils.getInstance().getInt(o.get(13)));
                        bd.setBstatus(RxUtils.getInstance().getInt(o.get(14)));
                        bd.setPrice_type(RxUtils.getInstance().getInt(o.get(15)));
//                        bd.setMinimum(Double.parseDouble((String) (o.get(16))));
                        String s1 = String.valueOf(o.get(16));
                        bd.setMinimum(s1);
                        bd.setCoefficient((Double) (o.get(17)));
                        bd.setRate((Double) (o.get(18)));
                        bd.setNum(RxUtils.getInstance().getInt(o.get(19)));
                        bd.setClass_code((String) o.get(20));
                        bd.setUid(String.valueOf((Double) o.get(21)));
                    }
                    Log.d("投注单详情2", bd.toString());
                    listener.onReceivedData(API_ID_BETTINGDETAIL, bd, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //撤销投注单
    public void getLotteryBetRevoke1(final DataListener listener, int bid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> lotteryBetRevoke1 = apiInterface.getLotteryBetRevoke1(bid, 1, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = lotteryBetRevoke1.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("撤销投注单", response.body().toString());
                    listener.onReceivedData(API_ID_BETREVOKE1, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }


    //添加会员-保存
    public void getVIPSignUpSave(final DataListener listener, String u, String n, double l, String code, int t, String p, String c) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("u", u + "");
        map.put("n", n + "");
        map.put("l", l + "");
        map.put("code", code + "");
        map.put("p", p + "");
        map.put("c", c + "");
        String registersReqkey = RxUtils.getInstance().getRegistersReqkey(map, t);
        Call<RestultInfo> vipSignUpSave = apiInterface.getVIPSignUpSave(1, u, n, l, code, t, p, c, registersReqkey);
        Call<RestultInfo> clone = vipSignUpSave.clone();
        Log.d("添加会员-保存请求完全体", clone.request().toString());
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {

                if (response.code() == 200) {
                    Log.d("添加会员-保存", response.body().toString());
                    listener.onReceivedData(API_ID_ADDCIPSAVE, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //会员管理
    public void getTeamUserInfo(final DataListener listener, int page, int rows, String sidx, String sord, int uid, String u, /*double b1, double b2, double l1, double l2,*/ int t) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("uid", uid + "");
        map.put("u", u + "");
      /*  map.put("b1", b1 + "");
        map.put("b2", b2 + "");
        map.put("l1", l1 + "");
        map.put("l2", l2 + "");*/
        String registersReqkey = RxUtils.getInstance().getRegistersReqkey(map, t);
        final Call<Map<String, Object>> teamUserInfo = apiInterface.getTeamUserInfo(1, page, rows, sidx, sord, uid, u, /*b1, b2, l1, l2,*/ t, registersReqkey);
        Call<Map<String, Object>> clone = teamUserInfo.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                List<List<TeamUserInfo>> ts = new ArrayList<List<TeamUserInfo>>();
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("会员管理", s);
                    List<List<Object>> con = new ArrayList<List<Object>>();
                    double totalElements = 0;
                    Map<String, Object> map = response.body();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if ("totalElements".equals(entry.getKey())) {
                            totalElements = (double) entry.getValue();
                        }
                        if ("content".equals(entry.getKey())) {
                            con = (List<List<Object>>) entry.getValue();
                        }
                    }
                    List<TeamUserInfo> t = new ArrayList<TeamUserInfo>();
                    TeamUserInfo teamUserInfo1 = new TeamUserInfo();
                    String st = totalElements + "";
                    st = st.substring(0, st.length() - 2);
                    teamUserInfo1.setTotalElements(Integer.parseInt(st));
                    t.add(teamUserInfo1);
                    ts.add(t);
                    if (totalElements > 0) {
                        String s1 = null;
                        List<TeamUserInfo> t1 = new ArrayList<TeamUserInfo>();
                        for (int i = 0; i < con.size(); i++) {
                            List<Object> cs = con.get(i);
                            TeamUserInfo team = new TeamUserInfo();
                            double uid = (double) cs.get(0);
                            double mine = (double) cs.get(1);
                            double utype = (double) cs.get(2);
                            String name = (String) cs.get(3);
                            double amount = (double) cs.get(4);
                            double rebate_id = (double) cs.get(5);
                            double rate = (double) cs.get(6);
                            String created = (String) cs.get(7);
                            String login = (String) cs.get(8);
                            double rtype = (double) cs.get(9);
                            double status = (double) cs.get(10);
                            double zu = (double) cs.get(11);
                            team.setUid(RxUtils.getInstance().getInt(uid));
                            team.setMine(RxUtils.getInstance().getInt(mine));
                            team.setUtype(RxUtils.getInstance().getInt(utype));
                            team.setName(name);
                            team.setAmount(amount);
                            team.setRebate_id(RxUtils.getInstance().getInt(rebate_id));
                            team.setRate(rate);
                            team.setCreated(created);
                            team.setLogin(login);
                            team.setRtype(RxUtils.getInstance().getInt(rtype));
                            team.setStatus(RxUtils.getInstance().getInt(status));
                            team.setZu(RxUtils.getInstance().getInt(zu));
                            t1.add(team);
                        }

                       /* String Content = s.substring(s.indexOf("content=[") + 9, s.indexOf("], number="));
                        String[] cs = Content.split(", ");
                        for (int i = 0; i < cs.length; i = i + 12) {
                            Log.d("会员管理Split", cs[i]);
                            TeamUserInfo team = new TeamUserInfo();
                            String uid = cs[i].substring(1, cs[i].length() - 2);
                            String mine = cs[i + 1].substring(0, cs[i + 1].length() - 2);
                            String utype = cs[i + 2].substring(0, cs[i + 2].length() - 2);
                            String name = cs[i + 3];
                            String amount = cs[i + 4];
                            String rebate_id = cs[i + 5].substring(0, cs[i + 5].length() - 2);
                            String rate = cs[i + 6];
                            String created = cs[i + 7];
                            String login = cs[i + 8];
                            String rtype = cs[i + 9].substring(0, cs[i + 9].length() - 2);
                            String status = cs[i + 10].substring(0, cs[i + 10].length() - 2);
                            String zu = cs[i + 11].substring(0, cs[i + 11].length() - 3);
                            team.setUid(Integer.parseInt(uid));
                            team.setMine(Integer.parseInt(mine));
                            team.setUtype(Integer.parseInt(utype));
                            team.setName(name);
                            team.setAmount(Double.parseDouble(amount));
                            team.setRebate_id(Integer.parseInt(rebate_id));
                            team.setRate(Double.parseDouble(rate));
                            team.setCreated(created);
                            team.setLogin(login);
                            team.setRtype(Integer.parseInt(rtype));
                            team.setStatus(Integer.parseInt(status));
                            team.setZu(Integer.parseInt(zu));
                            t1.add(team);
                        }*/
                        ts.add(t1);
                    }
                    listener.onReceivedData(API_ID_TEAMUSERINFO, ts, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //获取会员管理层级数据
    public void getTeamUsersParent(DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> teamUsersParent = apiInterface.getTeamUsersParent(uid, 1, reqkey, currentTimeMillis);
        Log.d("获取会员管理层级数据reqkey", reqkey);
        Log.d("获取会员管理层级数据的请求", teamUsersParent.request().toString());
        Call<Object> clone = teamUsersParent.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                int code = response.code();
                Log.d("获取会员管理层级数据Code", code + "");
                if (code == 200) {
                    Log.d("获取会员管理层级数据", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //查询团队余额
    public void getTeamBalanceView(final DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> teamBalanceView = apiInterface.getTeamBalanceView(1, uid, reqkey, currentTimeMillis);
        Call<Object> clone = teamBalanceView.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    String TeamAmount = "";
                    String msg = response.body().toString();
                    Log.d("TeamU查询团队余额", msg);
                    String rc = msg.substring(msg.indexOf("rc=") + 3, msg.indexOf(", msg="));
                    if ("true".equals(rc)) {
                        TeamAmount = msg.substring(msg.indexOf("msg=") + 4, msg.indexOf(", id"));
                    }
                    listener.onReceivedData(API_ID_TEAMBALANCEVIEW, TeamAmount, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //获取设置返点的数据
    public void getUreBateData(final DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> ureBateData = apiInterface.getUreBateData(1, uid, reqkey, currentTimeMillis);
        Call<Object> clone = ureBateData.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("TeamU获取设置返点的数据", s);
                    String others = s.substring(s.indexOf("others=") + 7, s.length() - 2);
                    String[] split = others.split(", ");
                    String max = "";
                    String min = "";
                    for (int i = 0; i < split.length; i++) {
                        Log.d("TeamU获取设置返点的数据Split", split[i]);
                        if (split[i].contains("mxrate=")) {
                            max = split[i].substring(split[i].indexOf("mxrate=") + 7, split[i].length());
                        }
                        if (split[i].contains("mnrate=")) {
                            min = split[i].substring(split[i].indexOf("mnrate=") + 7, split[i].length());
                        }
                    }
                    SetRate setRate = new SetRate();
                    setRate.setMxrate(Double.parseDouble(max));
                    setRate.setMnrate(Double.parseDouble(min));
                    Log.d("TeamU获取设置返点的数据String", setRate.toString());
                    listener.onReceivedData(API_ID_UREBATEDATA, setRate, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }


    //设置新返点
    public void getTeamUserRebateSave(final DataListener listener, double l, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("l", l + "");
        map.put("uid", uid + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> TeamUserRebateSave = apiInterface.getTeamUserRebateSave(1, l, uid, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = TeamUserRebateSave.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("设置新返点", response.body().toString());
                    listener.onReceivedData(API_ID_SETRATESAVE, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {
                Log.d("设置新返点", t.toString());
            }
        });
    }

    //获取上级充值数据
    public void getSreChargeData(final DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> SreChargeData = apiInterface.getSreChargeData(1, uid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = SreChargeData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取上级充值数据", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }

                    String ruser = null;
                    String username = null;
                    Boolean haspass = false;
                    Boolean hassqas = false;
                    String q = null;
                    Double amounts1 = null;
                    Double min1 = null;
                    Double max1 = null;
                    Double amounts2 = null;
                    Double min2 = null;
                    Double max2 = null;
                    int stype = 0;
                    Boolean dtype = null;
                    if (others.size() > 0) {
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("ruser")) {
                                ruser = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("username")) {
                                username = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("haspass")) {
                                haspass = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("hassqas")) {
                                hassqas = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("q")) {
                                q = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts1")) {
                                amounts1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min1")) {
                                min1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max1")) {
                                max1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts2")) {
                                amounts2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min2")) {
                                min2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max2")) {
                                max2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("stype")) {
                                double styped = (Double) entry.getValue();
                                stype = RxUtils.getInstance().getInt(styped);
                            }
                            if (entry.getKey().equals("dtype")) {

                                dtype = (Boolean) entry.getValue();
                            }
                        }
                    }


                    SreCharge sreCharge = new SreCharge();
                    sreCharge.setRuser(ruser);
                    sreCharge.setUsername(username);
                    sreCharge.setHaspass(haspass);
                    sreCharge.setHassqas(hassqas);
                    sreCharge.setQ(q);
                    sreCharge.setAmounts1(amounts1);
                    sreCharge.setAmounts2(amounts2);
                    sreCharge.setMin1(min1);
                    sreCharge.setMin2(min2);
                    sreCharge.setMax1(max1);
                    sreCharge.setMax2(max2);
                    sreCharge.setStype(stype);
                    sreCharge.setDtype(dtype);
                    listener.onReceivedData(API_ID_SRECHARGE, sreCharge, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    } //获取上级充值数据

    public void getSreChargeData2(final DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> SreChargeData = apiInterface.getSreChargeData(1, uid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = SreChargeData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取上级充值数据", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                /*    String others1 = s.substring(s.indexOf("others={") + 8, s.length() - 2);
                    String[] so = others.split(", ");
                    for (int i = 0; i < so.length; i++) {
                        Log.d("获取上级充值数据Split", so[i]);
                    }
                    int i = 0;*/
                    String ruser = null;
                    String username = null;
                    Boolean haspass = false;
                    Boolean hassqas = false;
                    String q = null;
                    Double amounts1 = null;
                    Double min1 = null;
                    Double max1 = null;
                    Double amounts2 = null;
                    Double min2 = null;
                    Double max2 = null;
                    int stype = 0;
                    Boolean dtype = null;
                    if (others.size() > 0) {
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                ruser = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("username")) {
                                username = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("haspass")) {
                                haspass = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("hassqas")) {
                                hassqas = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("q")) {
                                q = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts1")) {
                                amounts1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min1")) {
                                min1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max1")) {
                                max1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts2")) {
                                amounts2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min2")) {
                                min2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max2")) {
                                max2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("stype")) {
                                double styped = (Double) entry.getValue();
                                stype = RxUtils.getInstance().getInt(styped);
                            }
                            if (entry.getKey().equals("dtype")) {

                                dtype = (Boolean) entry.getValue();
                            }
                        }
                    }


                    SreCharge sreCharge = new SreCharge();
                    sreCharge.setRuser(ruser);
                    sreCharge.setUsername(username);
                    sreCharge.setHaspass(haspass);
                    sreCharge.setHassqas(hassqas);
                    sreCharge.setQ(q);
                    sreCharge.setAmounts1(amounts1);
                    sreCharge.setAmounts2(amounts2);
                    sreCharge.setMin1(min1);
                    sreCharge.setMin2(min2);
                    sreCharge.setMax1(max1);
                    sreCharge.setMax2(max2);
                    sreCharge.setStype(stype);
                    sreCharge.setDtype(dtype);
                    listener.onReceivedData(API_ID_SRECHARGE2, sreCharge, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }//获取上级充值数据

    public void getSreChargeData3(final DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> SreChargeData = apiInterface.getSreChargeData(1, uid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = SreChargeData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    String s = response.body().toString();
                    Log.d("获取上级充值数据", s);
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                /*    String others1 = s.substring(s.indexOf("others={") + 8, s.length() - 2);
                    String[] so = others.split(", ");
                    for (int i = 0; i < so.length; i++) {
                        Log.d("获取上级充值数据Split", so[i]);
                    }
                    int i = 0;*/
                    String ruser = null;
                    String username = null;
                    Boolean haspass = false;
                    Boolean hassqas = false;
                    String q = null;
                    Double amounts1 = null;
                    Double min1 = null;
                    Double max1 = null;
                    Double amounts2 = null;
                    Double min2 = null;
                    Double max2 = null;
                    int stype = 0;
                    Boolean dtype = null;
                    if (others.size() > 0) {
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                ruser = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("username")) {
                                username = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("haspass")) {
                                haspass = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("hassqas")) {
                                hassqas = (Boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("q")) {
                                q = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts1")) {
                                amounts1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min1")) {
                                min1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max1")) {
                                max1 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("amounts2")) {
                                amounts2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("min2")) {
                                min2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("max2")) {
                                max2 = (Double) entry.getValue();
                            }
                            if (entry.getKey().equals("stype")) {
                                double styped = (Double) entry.getValue();
                                stype = RxUtils.getInstance().getInt(styped);
                            }
                            if (entry.getKey().equals("dtype")) {

                                dtype = (Boolean) entry.getValue();
                            }
                        }
                    }


                    SreCharge sreCharge = new SreCharge();
                    sreCharge.setRuser(ruser);
                    sreCharge.setUsername(username);
                    sreCharge.setHaspass(haspass);
                    sreCharge.setHassqas(hassqas);
                    sreCharge.setQ(q);
                    sreCharge.setAmounts1(amounts1);
                    sreCharge.setAmounts2(amounts2);
                    sreCharge.setMin1(min1);
                    sreCharge.setMin2(min2);
                    sreCharge.setMax1(max1);
                    sreCharge.setMax2(max2);
                    sreCharge.setStype(stype);
                    sreCharge.setDtype(dtype);
                    listener.onReceivedData(API_ID_SRECHARGE3, sreCharge, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //上级充值校验
    public void getOwnReansferCheck(DataListener listener, String a, String p) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("a", a + "");
        map.put("p", p + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> OwnReansferCheck = apiInterface.getOwnReansferCheck(1, a, p, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = OwnReansferCheck.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("上级充值校验", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //保存充值校验
    public void getOwnReansferTrans(final DataListener listener, double amount, String name) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("amount", amount + "");
        map.put("name", name + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> OwnReansferTrans = apiInterface.getOwnReansferTrans(1, amount, name, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = OwnReansferTrans.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("保存充值校验", response.body().toString());
                    listener.onReceivedData(API_ID_OWNTRANSFER, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //保存日工资充值
    public void getDailyRechargeTrans(final DataListener listener, double amount, String name) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("amount", amount + "");
        map.put("name", name + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> dailyRechargeTrans = apiInterface.getDailyRechargeTrans(1, amount, name, reqkey, currentTimeMillis);
        Log.d("保存日工资充值请求", dailyRechargeTrans.request().toString());
        Call<RestultInfo> clone = dailyRechargeTrans.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("保存日工资充值", response.body().toString());
                    listener.onReceivedData(API_ID_DAILYRECHARGE, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //获取设置配额的数据
    public void getTquotaData(DataListener listener, int uid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> tquotaData = apiInterface.getTquotaData(1, uid, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = tquotaData.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {

                    Map<String, Object> other = new HashMap<>();
                    String s = response.body().toString();
                    Map<String, Object> map = response.body();
                    int uid;
                    String name;
                    List<List<Object>> qlist = new ArrayList<List<Object>>();
                    Log.d("获取设置配额的数据", s);
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("others".equals(entry.getKey())) {
                                other = (Map<String, Object>) entry.getValue();
                            }
                        }
                        for (Map.Entry<String, Object> entry : other.entrySet()) {
                            if ("uid".equals(entry.getKey())) {
                                String u = (String) entry.getValue();
                                u = u.substring(0, u.length() - 2);
                                uid = Integer.parseInt(u);
                            }
                            if ("name".equals(entry.getKey())) {
                                name = (String) entry.getValue();
                            }
                            if ("qlist".equals(entry.getKey())) {
                                qlist = (List<List<Object>>) entry.getValue();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //保存配额
    public void getTeamUserQuotaSave(DataListener listener, int page, int rows, String sidx, String sord, int uid, String from, String to, int id, int stype, int model) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("uid", uid + "");
        map.put("from", from + "");
        map.put("to", to + "");
        map.put("id", id + "");
        map.put("stype", stype + "");
        map.put("model", model + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> teamUserQuotaSave = apiInterface.getTeamUserQuotaSave(1, page, rows, sidx, sord, uid, from, to, id, stype, model, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = teamUserQuotaSave.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("保存配额", response.toString());
                }


            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });


    }


    //帐变记录
    public void getCapitalChangeList(final DataListener listener, int page, int rows, String sidx, String sord, int uid, String from, String to, int id, int stype, int model) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("sidx", sidx + "");
        map.put("sord", sord + "");
        map.put("uid", uid + "");
        map.put("from", from + "");
        map.put("to", to + "");
        map.put("id", id + "");
        map.put("stype", stype + "");
        map.put("model", model + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> capitalChangeList = apiInterface.getCapitalChangeList(1, page, rows, sidx, sord, uid, from, to, id, stype, model, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = capitalChangeList.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("帐变记录", response.body().toString());
                    Map<String, Object> map = response.body();
                    List<Object> content = new ArrayList<>();
                    List<VIPAccountChange> vp = new ArrayList<VIPAccountChange>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("content".equals(entry.getKey())) {
                                content = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    for (int i = 0; i < content.size(); i++) {
                        List<Object> o = (List<Object>) content.get(i);
                        for (int j = 0; j < o.size(); j = j + 10) {
                            double atid = (double) o.get(j);
                            String serial_number = (String) o.get(j + 1);
                            String draw_period = (String) o.get(j + 2);
                            String time = (String) o.get(j + 3);
                            String game = (String) o.get(j + 4);
                            double type = (double) o.get(j + 5);
                            double price_unit = (double) o.get(j + 6);
                            double amount = (double) o.get(j + 7);
                            double balance = (double) o.get(j + 8);
                            String remark = (String) o.get(j + 9);
                            VIPAccountChange v = new VIPAccountChange();
                            v.setAtid(RxUtils.getInstance().getInt(atid));
                            v.setSerial_number(serial_number);
                            v.setDraw_period(draw_period);
                            v.setTime(time);
                            v.setGame(game);
                            v.setType(RxUtils.getInstance().getInt(type));
                            v.setPrice_unit(RxUtils.getInstance().getInt(price_unit));
                            v.setAmount(amount);
                            v.setBalance(balance);
                            v.setRemark(remark);
                            vp.add(v);
                        }
                    }
                    listener.onReceivedData(API_ID_VIPACCCHANGE, vp, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //跟新奖金类型
    public void getBettingUpdatePrice(DataListener listener, int gid) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> BettingUpdatePrice = apiInterface.getBettingUpdatePrice(gid, 1, reqkey, currentTimeMillis);
        Call<Object> clone = BettingUpdatePrice.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("跟新奖金类型", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //获取追号信息
    public void getBettingAutoPurchase(final DataListener listener, int id, String period, int periods) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("period", period + "");
        map.put("periods", periods + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> bettingAutoPurchase = apiInterface.getBettingAutoPurchase(1, id, period, periods, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = bettingAutoPurchase.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("获取追号信息Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("获取追号信息", response.body().toString());
                    Map<String, Object> map = response.body();
                    List<ZhuiHaoCNum> zhCNum = new ArrayList<>();
                    List<Object> content = new ArrayList<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("content")) {
                                content = (List<Object>) entry.getValue();
                            }
                        }
                    }
                    for (int i = 0; i < content.size(); i++) {
                        List<String> o = (List<String>) content.get(i);
                        ZhuiHaoCNum zhuiHaoCNum = new ZhuiHaoCNum();
                        zhuiHaoCNum.setPeriod(o.get(0));
                        zhuiHaoCNum.setDate(o.get(1));
                        zhCNum.add(zhuiHaoCNum);
                    }
                    listener.onReceivedData(API_ID_BEETING_AUTO, zhCNum, API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("获取追号信息Error", t.toString());
            }
        });
    }

    //91获取全部追号的期数
    public void getBettingAutoNums(DataListener listener, int id, String period) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("period", period + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> BettingAutoNums = apiInterface.getBettingAutoNums(1, id, period, reqkey, currentTimeMillis);
        Call<Object> clone = BettingAutoNums.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("91获取全部追号的期数", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //同步上期开奖数据
    public void getBettingWinningNum(final DataListener listener, int id, String period) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("period", period + "");
        final String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> BettingWinningNum = apiInterface.getBettingWinningNum(1, id, period, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = BettingWinningNum.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    Log.d("同步上期开奖数据", response.body().toString());
                    Map<String, Object> map = response.body();
                    boolean rc = false;
                    double id = 0;
                    Map<String, Object> others = new HashMap<>();
                    BettingSync bettingSync = new BettingSync();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if ("rc".equals(entry.getKey())) {
                                rc = (boolean) entry.getValue();
                            }
                            if ("id".equals(entry.getKey())) {
                                id = (double) entry.getValue();
                            }
                            if ("others".equals(entry.getKey())) {
                                others = (Map<String, Object>) entry.getValue();
                            }

                        }
                    }
                    bettingSync.setRc(rc);
                    bettingSync.setGid(RxUtils.getInstance().getInt(id));
                    if (others.size() > 0) {
                        String property = null;
                        String period = null;
                        String winningNumber = null;
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if ("property".equals(entry.getKey())) {
                                property = (String) entry.getValue();
                            }
                            if ("period".equals(entry.getKey())) {
                                period = (String) entry.getValue();
                            }
                            if ("winningNumber".equals(entry.getKey())) {
                                winningNumber = (String) entry.getValue();
                            }
                        }
                        bettingSync.setProperty(property);
                        bettingSync.setPeriod(period);
                        bettingSync.setWinningNumber(winningNumber);
                    }
                    if (rc) {
                        listener.onReceivedData(API_ID_BETTINGWINNUM, bettingSync, API_ID_ERROR);
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //投注记录
    public void getBettingHistorys(DataListener listener, int id, int page) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> bettingHistorys = apiInterface.getBettingHistorys(id, 1, page, reqkey, currentTimeMillis);
        Call<Object> clone = bettingHistorys.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("投注记录", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //追号记录
    public void getBettingRebuyHistorys(DataListener listener, int id, int page) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Object> bettingRebuyHistorys = apiInterface.getBettingRebuyHistorys(id, 1, page, reqkey, currentTimeMillis);
        Call<Object> clone = bettingRebuyHistorys.clone();
        clone.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if (response.code() == 200) {
                    Log.d("追号记录", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    //验证安全问题
    public void getSecurityQuestionCheck(final DataListener listener, String a) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("a", a + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> securityQuestionCheck = apiInterface.getSecurityQuestionCheck(1, a, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = securityQuestionCheck.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                if (response.code() == 200) {
                    Log.d("验证安全问题", response.body().toString());
                    listener.onReceivedData(API_ID_SAFEPWDCHECK, response.body(), API_ID_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });

    }

    //APP易宝支付初始参数获取
    public void getYeePay(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> getyeelay = apiInterface.getYeePay(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = getyeelay.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("APP易宝支付初始参数获取Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("APP易宝支付初始参数获取", response.body().toString());
                    Map<String, Object> map = response.body();
                    EBao eBao = new EBao();
                    Map<String, Object> others = new HashMap<>();
                    List<EBao.Others.Banks> banks1 = new ArrayList<>();
                    EBao eBao1 = new EBao();
                    EBao.Others others1 = eBao1.new Others();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("rc")) {
                                eBao.setRc((Boolean) entry.getValue());
                            }
                            if (entry.getKey().equals("msg")) {
                                eBao.setMsg((String) entry.getValue());
                            }
                            if (entry.getKey().equals("id")) {
                                eBao.setId(RxUtils.getInstance().getInt(entry.getValue()));
                            }
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    if (others.size() > 0) {
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("min")) {
                                others1.setMin(
                                        (Double) entry.getValue()
                                );
                            }
                            if (entry.getKey().equals("max")) {
                                others1.setMax((Double) entry.getValue());
                            }
                            if (entry.getKey().equals("freeze")) {
                                others1.setFreeze((Boolean) entry.getValue());
                            }
                            if (entry.getKey().equals("banks")) {
                                banks1 = (List<EBao.Others.Banks>) entry.getValue();
                            }
                        }
                    }
                    List<EBao.Others.Banks> bs = new ArrayList<>();
                    for (int i = 0; i < banks1.size(); i++) {
                        Map<String, Object> bk = (Map<String, Object>) banks1.get(i);
                        String code = "";
                        String name = "";
                        if (bk.size() > 0) {
                            for (Map.Entry<String, Object> entry : bk.entrySet()) {
                                if (entry.getKey().equals("code")) {
                                    code = (String) entry.getValue();
                                }
                                if (entry.getKey().equals("name")) {
                                    name = (String) entry.getValue();
                                }
                            }
                        }
                        EBao.Others.Banks banks2 = others1.new Banks();
                        banks2.setCode(code);
                        banks2.setName(name);
                        bs.add(banks2);
                    }
                    others1.setBanks(bs);

                    eBao.setOthers(others1);
                    Log.d("银行的信息Code_Name", eBao.toString());

                    listener.onReceivedData(API_ID_YEEPAY, eBao, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    } //易宝支付保存

    public void getOwnRechargeSave(final DataListener listener, String p5_Pid, String pd_FrpId, Integer amount) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("p5_Pid", p5_Pid);
        map.put("pd_FrpId", pd_FrpId);
        map.put("amount", amount + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> OwnRechargeSave = apiInterface.getOwnRechargeSave(1, p5_Pid, pd_FrpId, amount, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = OwnRechargeSave.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("易宝支付保存Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("易宝支付保存", response.body().toString());
                    listener.onReceivedData(API_ID_OWNRECHARGESAVE, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //三、支付宝支付
    public void getAliPay(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        final Call<Map<String, Object>> AliPay1 = apiInterface.getAliPay(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = AliPay1.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("支付宝支付Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("支付宝支付", response.body().toString());
                    Map<String, Object> map = response.body();
                    AliPay aliPay = new AliPay();
                    Map<String, Object> others = new HashMap<>();
                    AliPay.Others os = aliPay.new Others();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }

                    List<Remittance_Record> RR = new ArrayList<>();
                    if (others.size() > 0) {
                        double min = 0;
                        double max = 0;
                        boolean freeze = false;
                        List<Object> rr = new ArrayList<>();


                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("min")) {
                                min = (double) entry.getValue();
                            }
                            if (entry.getKey().equals("max")) {
                                max = (double) entry.getValue();
                            }
                            if (entry.getKey().equals("freeze")) {
                                freeze = (boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("record")) {
                                rr = (List<Object>) entry.getValue();
                            }
                        }

                        for (int i = 0; i < rr.size(); i++) {
                            Remittance_Record Rs = new Remittance_Record();
                            List<Object> rs = (List<Object>) rr.get(i);
                            Rs.setDatetime((String) rs.get(1));
                            Rs.setAmount(RxUtils.getInstance().getInt(rs.get(2)));
                            Rs.setNote((String) rs.get(3));
                            Rs.setStatus(RxUtils.getInstance().getInt(rs.get(4)));
                            Rs.setRemark((String) rs.get(5));
                            RR.add(Rs);
                        }
                        os.setFreeze(freeze);
                        os.setMax(max);
                        os.setMin(min);
                        os.setRecords(RR);
                        aliPay.setOthers(os);

                    }
                    Log.d("支付宝支付ToString", aliPay.toString());
                    listener.onReceivedData(API_ID_ALIPAY, aliPay, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //APP支付宝支付保存
    public void getAliPlayReChargeSave(final DataListener listener, int type, Integer amount, String holders_name, String account_number) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("amount", amount + "");
        map.put("holders_name", holders_name + "");
        map.put("account_number", account_number + "");
        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<Map<String, Object>> AliPlayReChargeSave = apiInterface.getAliPlayReChargeSave(1, type, amount, holders_name, account_number, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = AliPlayReChargeSave.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("APP支付宝支付保存Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("APP支付宝支付保存", response.body().toString());
                    Map<String, Object> map = response.body();
                    AliPayBean aliPayBean = new AliPayBean();
                    Map<String, Object> others = new HashMap<>();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("rc")) {
                                aliPayBean.setRc((Boolean) entry.getValue());
                            }
                            if (entry.getKey().equals("msg")) {
                                aliPayBean.setMsg((String) entry.getValue());
                            }
                            if (entry.getKey().equals("id")) {
                                aliPayBean.setId(RxUtils.getInstance().getInt(entry.getValue()));
                            }
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    if (others.size() > 0) {
                        String note = "";
                        String bank_name = "";
                        String holders_name = "";
                        String account_number = "";
                        String bank_address = "";
                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("note")) {
                                note = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("bank_name")) {
                                bank_name = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("holders_name")) {
                                holders_name = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("account_number")) {
                                account_number = (String) entry.getValue();
                            }
                            if (entry.getKey().equals("bank_address")) {
                                bank_address = (String) entry.getValue();
                            }
                        }
                        AliPayBean.Others others1 = aliPayBean.new Others();
                        others1.setNote(note);
                        others1.setBank_name(bank_name);
                        others1.setHolders_name(holders_name);
                        others1.setAccount_number(account_number);
                        others1.setBank_address(bank_address);
                        aliPayBean.setOthers(others1);
                    }
                    Log.d("APP支付宝支付保存String", aliPayBean.toString());
                    listener.onReceivedData(API_ID_ALIPAYRECHARGESAVE, aliPayBean, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    //支付宝支付确认支付
    public void getAliPlayReChargeSubmit(final DataListener listener, int id, String note) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("note", note + "");

        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        Call<RestultInfo> AliPlayReChargeSubmit = apiInterface.getAliPlayReChargeSubmit(1, id, note, reqkey, currentTimeMillis);
        Call<RestultInfo> clone = AliPlayReChargeSubmit.clone();
        clone.enqueue(new Callback<RestultInfo>() {
            @Override
            public void onResponse(Call<RestultInfo> call, retrofit2.Response<RestultInfo> response) {
                Log.d("支付宝支付确认支付Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("支付宝支付确认支付", response.body().toString());
                    listener.onReceivedData(API_ID_ALIPAYSUBMIT, response.body(), API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RestultInfo> call, Throwable t) {

            }
        });
    }

    //转账汇款：APP支付宝支付初始参数获取
    public void getRemittance(final DataListener listener) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();


        String reqkey = RxUtils.getInstance().getReqkey(map, currentTimeMillis);
        final Call<Map<String, Object>> Remittances = apiInterface.getRemittance(1, reqkey, currentTimeMillis);
        Call<Map<String, Object>> clone = Remittances.clone();
        clone.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, retrofit2.Response<Map<String, Object>> response) {
                Log.d("转账汇款Code", response.code() + "");
                if (response.code() == 200) {
                    Log.d("转账汇款", response.body().toString());
                    Map<String, Object> map = response.body();
                    Map<String, Object> others = new HashMap<>();
                    Remittance remittance = new Remittance();
                    Remittance.Others os = remittance.new Others();
                    if (map.size() > 0) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("rc")) {
                                remittance.setRc((Boolean) entry.getValue());
                            }
                            if (entry.getKey().equals("msg")) {
                                remittance.setMsg((String) entry.getValue());
                            }
                            if (entry.getKey().equals("id")) {
                                remittance.setId(RxUtils.getInstance().getInt(entry.getValue()));
                            }
                            if (entry.getKey().equals("others")) {
                                others = (Map<String, Object>) entry.getValue();
                            }
                        }
                    }
                    List<Remittance_Record> RR = new ArrayList<>();
                    if (others.size() > 0) {
                        double min = 0;
                        double max = 0;
                        boolean freeze = false;
                        List<Object> rr = new ArrayList<>();


                        for (Map.Entry<String, Object> entry : others.entrySet()) {
                            if (entry.getKey().equals("min")) {
                                min = (double) entry.getValue();
                            }
                            if (entry.getKey().equals("max")) {
                                max = (double) entry.getValue();
                            }
                            if (entry.getKey().equals("freeze")) {
                                freeze = (boolean) entry.getValue();
                            }
                            if (entry.getKey().equals("record")) {
                                rr = (List<Object>) entry.getValue();
                            }
                        }

                        for (int i = 0; i < rr.size(); i++) {
                            Remittance_Record Rs = new Remittance_Record();
                            List<Object> rs = (List<Object>) rr.get(i);
                            Rs.setDatetime((String) rs.get(1));
                            Rs.setAmount(RxUtils.getInstance().getInt(rs.get(2)));
                            Rs.setNote((String) rs.get(3));
                            Rs.setStatus(RxUtils.getInstance().getInt(rs.get(4)));
                            Rs.setRemark((String) rs.get(5));
                            RR.add(Rs);
                        }
                        os.setFreeze(freeze);
                        os.setMax(max);
                        os.setMin(min);
                        os.setRR(RR);
                        remittance.setOthers(os);

                    }
                    Log.d("转账汇款ToString", remittance.toString());
                    listener.onReceivedData(API_ID_REMITTANCE, remittance, API_ID_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }


}