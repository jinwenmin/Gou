package com.example.king.gou.utils;

import android.text.TextUtils;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.NoticeContent;
import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    @Headers("X-Requested-With: XMLHttpRequest")

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
    Call<UserAmount> getUserAmount();

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

    //登陆
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-user-datas")
    Call<UserInfo> getUserInfo();

    //活动列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-activitys")
    Call<Object> getActivityList();

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-notices")
    Call<Object> getNotices();

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/notice-view/{id}")
    Call<Object> getNoticesContent(@Path("id") int id);

    //获取玩法
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-rules_description/{tid}")
    Call<Object> getGameType(@Path("tid") int tid);

    //获取游戏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-game-datas")
    Call<Object> getGame(@Query("type") int type,
                         @Query("gid") int gid,
                         @Query("tid") int tid,
                         @Query("ptid") int ptid);

    //获取奖金详情
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/prize-details-list")
    Call<Object> getPrizeDetails(@Query("rows") int rows,
                                 @Query("page") int page,
                                 @Query("sidx") String sidx,
                                 @Query("sord") String sord);

    //找回密码接口
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/back-pass-check")
    Call<Object> getBackPassword(@Query("u") String u,
                                 @Query("p") String p
    );

//找回密码保存

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/back-pass-save")
    Call<Object> getBackPasswordSave(@Query("u") String u,//用户名
                                     @Query("p") String p//密码
    );

    //找回密码保存

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/freeze-user-check")
    Call<Object> getFreezeUser(@Query("u") String u,//需要冻结的用户名
                               @Query("n") String n,//持卡人姓名
                               @Query("no") String no//已绑定的银行卡号
    );

    //领取活动奖金

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-notices-check")
    Call<Object> getActivityCheck(@Query("id") int id,//活动id
                                  @Query("alid") int alid//活动层级id

    );
}
