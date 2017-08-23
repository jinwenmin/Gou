package com.example.king.gou.utils;

import android.graphics.Bitmap;
import android.media.Image;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.NoticeContent;
import com.example.king.gou.bean.RestultInfo;

import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    //登出有日志
    @Headers("X-Requested-With: XMLHttpRequest")
    @GET("/logout")
    Call<Object> getLogout();

    //登出无日志
    @Headers("X-Requested-With: XMLHttpRequest")
    @GET("/signout")
    Call<Object> getSignout(
           /* @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t*/
    );

    //登陆状态查询
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/chat-message")
    Call<Map<String, Object>> getLoginState(

            @Query("luid") int luid,
            @Query("uonline") int uonline,
            @Query("type") int type,
            @Query("ids") String[] ids,
            @Query("gets") int gets
    );


    //查询用户余额
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/amount-refresh")
    Call<UserAmount> getUserAmount(

            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

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

    //用户信息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-user-datas")
    Call<UserInfo> getUserInfo(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //活动列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-activitys")
    Call<List<List<Object>>> getActivityList(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //活动列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-notices-view")
    Call<Map<String, Object>> getActivityNoticesView(
            @Query("AppClient") int num,
            @Query("id") int id,//活动id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //报名参加活动
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-user-apply")
    Call<Map<String, Object>> getActivityUserApply(
            @Query("AppClient") int num,
            @Query("id") int id,//活动id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-notices")
    Call<List<List<Object>>> getNotices(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
 /*   @FormUrlEncoded*/
    @POST("/notice-view/{id}")
    Call<Map<String, Object>> getNoticesContent(
            @Path("id") int id,
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //获取玩法
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-rules_description/{tid}")
    Call<Object> getGameType(@Path("tid") int tid,
                             @Query("AppClient") int num,
                             @Query("reqkey") String reqkey,
                             @Query("t") long t
    );

    //获取游戏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-game-datas")
    Call<List<Map<String, Object>>> getGame(
            @Query("AppClient") int num,
            @Query("type") int type,
            @Query("gid") int gid,
            @Query("tid") int tid,
            @Query("ptid") int ptid,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //获取奖金详情
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/prize-details-list")
    Call<Map<String, Object>> getPrizeDetails(
            @Query("AppClient") int a,
            @Query("rows") int rows,
            @Query("page") int page,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("id") int id,
            @Query("rid") int rid,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //查询游戏的开奖记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/lottery-record-list")
    Call<Map<String, Object>> getRecordList(
            @Query("AppClient") int a,
            @Query("rows") int rows,
            @Query("page") int page,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("id") int id,
            @Query("period") String period,
            @Query("start") String start,
            @Query("end") String end,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //找回密码接口
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/back-pass-check")
    Call<Object> getBackPassword(
            @Query("AppClient") int num,
            @Query("u") String u,
            @Query("p") String p,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );


    //找回密码保存
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/back-pass-save")
    Call<Object> getBackPasswordSave(
            @Query("AppClient") int num,
            @Query("u") String u,//用户名
            @Query("p") String p,//密码
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //找回密码保存

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/freeze-user-check")
    Call<Object> getFreezeUser(
            @Query("AppClient") int num,
            @Query("u") String u,//需要冻结的用户名
            @Query("n") String n,//持卡人姓名
            @Query("no") String no,//已绑定的银行卡号
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //领取活动奖金

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-notices-check")
    Call<RestultInfo> getActivityCheck(
            @Query("AppClient") int num,
            @Query("id") int id,//活动id
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );
    //领取活动奖金加aid

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-notices-check")
    Call<Object> getActivityCheckAid(
            @Query("AppClient") int num,
            @Query("id") int id,//活动id
            @Query("alid") int alid,//活动层级id
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );


    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/profile-save")
    Call<RestultInfo> getNickNameChange(
            @Query("AppClient") int num,
            @Query("nickname") String nickname,//用户新昵称
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //强制修改初始密码
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/password-manage-save2")
    Call<RestultInfo> getUpdateFirstPwd(
            @Query("AppClient") int num,
            @Query("p0") String p0,//初始密码
            @Query("p1") String p1,//新密码
            @Query("p2") String p2,//安全密码
            @Query("email") String email,//邮箱
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取安全问题
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-questions")
    Call<List<List<Object>>> getSafeQues(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取首页的公告或者滚动栏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/notice-view-pop")
    Call<Map<String, Object>> getHomeNotice(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //修改登录密码
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/password-manage-save")
    Call<RestultInfo> getUpDatePwd(
            @Query("AppClient") int num,
            @Query("p0") String p0,//原密码
            @Query("p1") String p1,//新密码
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //验证安全密码
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/own-withdraw-check")
    Call<RestultInfo> getCheckSafePwd(
            @Query("AppClient") int num,
            @Query("p") String p,//安全密码，加密方式同登录密码
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //保存安全问题
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/security-question-save")
    Call<RestultInfo> getSaveSafeQues(
            @Query("AppClient") int num,
            @Query("q") String q,//安全问题
            @Query("a") String a,//安全问题的答案
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取绑定银行卡数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-card-datas")
    Call<Map<String, Object>> getCardDatas(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //验证银行卡号
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/check-bankcard-resource")
    Call<Object> getCheckBankCardNum(
            @Query("AppClient") int num,
            @Query("name") String name,//持卡人姓名
            @Query("card") String card,//银行卡号
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //获取省市级联
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-city-datas")
    Call<List<List<Object>>> getPrivens(
            @Query("AppClient") int num,
            @Query("id") int id,//省份id
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //保存银行卡  应用场景：绑定新卡保存银行卡数据

    /*{
    bank：bank，
    province_id：province_id，
    province：province，
    city_id：city_id，
    city：city，
    branch：branch，
    name：name，
    card：card

    bank[int]：开户银行id
    province_id[int]：省份id
    province[string]：省份
    city_id[int]：城市id
    city[string]：城市
    branch[string]：支行名称
    name[string]：持卡人姓名
    card[string]：银行卡号
    }*/
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/binding-card-save")
    Call<RestultInfo> getSaveBankCard(
            @Query("AppClient") int num,
            @Query("bank") int bank,//开户银行id
            @Query("province_id") int province_id,//省份id
            @Query("province") String province,//省份
            @Query("city_id") int city_id,//城市id
            @Query("city") String city,//城市
            @Query("branch") String branch,//支行名称
            @Query("name") String name,//持卡人姓名
            @Query("card") String card,//银行卡号
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //获取提现数据
    //使用场景：提现界面数据初始(提现功能先必须验证安全密码，通过后才能提现)
    //请求地址：/get-withdraw-datas
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-withdraw-datas")
    Call<Map<String, Object>> getWithDrawData(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //添加提现申请
    //  应用场景：提现验证通过后创建提现申请
    // 请求地址：/own-withdraw-create
    // 请求模式：POST
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/own-withdraw-create")
    Call<RestultInfo> getWithDrawCreate(
            @Query("AppClient") int num,
            @Query("aid") int aid,//收款银行卡id
            @Query("amount") BigDecimal amount,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );//提现金额

    //修改安全密码
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/security-password-save")
    Call<RestultInfo> getUpDataSafePwd(
            @Query("AppClient") int num,
            @Query("p0") String p0,//原安全密码，加密方式同登录密码
            @Query("p1") String p1,
            @Query("email") String email,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //充值安全密码或者登录密码
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/safetypassword-back-save")
    Call<RestultInfo> getResetPwd(
            @Query("AppClient") int num,
            @Query("type") int type,//type[int]：重置类型  1：重置登录密码  2：重置安全密码
            @Query("p") String p,//新密码 或者 新安全密码
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //锁定绑定银行卡
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/binding-card-lock")
    Call<RestultInfo> getBindingCardLock(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取聊天用户
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-chat-users")
    Call<Object> getChatUsers(

            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //查询消息列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/message-manage-list")
    Call<Map<String, Object>> getChatList(

            @Query("AppClient") int num,

            @Query("page") int page,//查询的页码
            @Query("rows") int rows,//每页查询的记录数
            @Query("sidx") String sidx,//排序参数，这里是[chat_date]
            @Query("sord") String sord,//排序类型，这里是[desc]
            @Query("send_uid") int send_uid,//查询用户id
            @Query("from") String from,//查询开始时间[yyyy-MM-dd HH:mm:ss]
            @Query("to") String to,//查询结束时间[yyyy-MM-dd HH:mm:ss]
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //删除聊天信息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/message-manage-delete")
    Call<RestultInfo> getDeleteChatMsg(
            @Query("AppClient") int num,
            @Query("id") int id,//聊天消息id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //加载聊天信息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/message-manage-loads")
    Call<Object> getChatMsg(
            @Query("AppClient") int num,
            @Query("id") int id,//聊天消息id
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //发送聊天信息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/chat-message-send")
    Call<RestultInfo> getSendMsg(
            @Query("AppClient") int num,
            @Query("id") int id,//聊天消息id
            @Query("title") String title, //聊天消息id
            @Query("msg") String msg,//聊天消息id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //轮询读取新消息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/message-manage-getnew")
    Call<List<List<Object>>> getNewMsg(
            @Query("AppClient") int num,
            @Query("id") int id,//聊天对象uid
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //投注单详情
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-details/{id}")
    Call<Object> getBettingDetails(
            @Path("id") int id,//id为投注单bid
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //查询投注记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-record-list")
    Call<Map<String, Object>> getBettingRecordList(
            @Query("AppClient") int num,
            @Query("page") int page,//查询的页码
            @Query("rows") int rows,//每页查询的记录数
            @Query("sidx") String sidx,//排序参数，这里是[serial_number]
            @Query("sord") String sord,//排序类型，这里是[desc]
            @Query("from") String from,//查询开始时间[yyyy-MM-dd HH:mm:ss]
            @Query("to") String to,//查询结束时间[yyyy-MM-dd HH:mm:ss]
            @Query("id") int id,//游戏id
            @Query("rid") int rid,//玩法id
            @Query("status") int status,//投注单状态
            //-1：全部
            //0：未购买
            //1：未开奖
            //2：本人撤单
            //3：管理员撤单
            //4：已过期
            //5：未中奖
            //6：平台撤单
            //7：已派奖
            @Query("rebuy") String rebuy,
            @Query("period") String period,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //撤销投注单
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/lottery-bet-revoke/{bid}")
    Call<RestultInfo> getLotteryBetRevoke1(
            @Path("bid") int id,//购彩单id
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //查询追号记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/keep-number-list")
    Call<Object> getKeepNum(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("rid") int rid,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //47 查询追号详情
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/auto-purchase-details/{id}")
    Call<Object> getKeepNumDetails(
            @Path("id") int id,//id为追号记录id
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //48 查询追号投注记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/keep-number-bet-list")
    Call<Object> getKeepNumBet(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("id") int id,//追号记录id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //49 批量撤单(停止追号)
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/lottery-betlist-revoke")
    Call<RestultInfo> getLotteryBetRevoke(
            @Query("AppClient") int num,
            @Query("ids") String ids,//勾选的投注单id集；逗号分隔
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //50 查询个人报表彩票帐变
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/account-change-list")
    Call<Map<String, Object>> getAccountChangeList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("type") int type,/*查询类型
                                        0：个人报表
                                        1：团队报表*/
            @Query("stype") int stype,
                                        /*帐变类型
                                        0：所有类型
                                        1：加入游戏
                                        2：投注返点
                                        3：奖金派送
                                        4：追号扣款
                                        5：当期追号返款
                                        6：游戏扣款
                                        7：撤单返款
                                        8：撤销返点
                                        9：撤销派奖*/
            @Query("model") int model,
                                            /*投注模式
                                            0：所有模式
                                            1：元
                                            2：角
                                            3：分
                                            4：厘*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //52 个人报表活动记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-record-list")
    Call<Map<String, Object>> getActivityRecordList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,

            @Query("type") int type,/*查询类型
                                        0：所有类型
                                        22：活动奖金
                                        29：亏损佣金
                                        30：消费佣金*/

            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //51 个人报表充提记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/recharge-withdraw-list")
    Call<Map<String, Object>> getReChargeWithDrawList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,

            @Query("type") int type,/*查询类型
                                        0：所有类型
                                        22：活动奖金
                                        29：亏损佣金
                                        30：消费佣金*/

            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //53 个人报表彩票投注
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-list")
    Call<Object> getBettingList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("rid") int rid,
            @Query("status") int status,
                                    /*投注单状态
                                    -2：所有状态
                                    -1：未开奖
                                    0：未中奖
                                    1：已派奖
                                    2：本人撤单
                                    3：管理员撤单
                                    4：已过期
                                    6：平台撤单*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //54 个人报表彩票盈亏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/profit-loss-list")
    Call<Map<String, Object>> getProfitLossList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("gtype") int gtype,
                            /*查询类型
                            1：彩票娱乐场
                            2：香港六合彩
                            0：所有类型*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //55 获取大数据投注内容
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-data")
    Call<Object> getGetMoreData(
            @Query("AppClient") int num,
            @Query("id") int id,//id为投注单bid
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //57 获取验证码图片
    @Headers("X-Requested-With: XMLHttpRequest")
    @GET("/captcha")
    Call<Object> getCaptCha(

            @Query("t") long t1//时间戳，用于防止验证码图片缓存

    );
//

    //58 验证码校验
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/captcha-check")
    Call<Object> getCaptChaCheck(
            @Query("AppClient") int num,
            @Query("u") String u,//用户名(如果有，主要用于获取预保留验证信息)
            @Query("c") String c,//4位验证码
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //51 个人报表充提记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/recharge-withdraw-list")
    Call<Object> getTeamReChargeWithDrawList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("name") String name,

            @Query("type") int type,
            @Query("team") int team,

            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //82 查询团队报表彩票帐变
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/account-change-list")
    Call<Map<String, Object>> getTeamAccountChangeList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("name") String name,
            @Query("type") int type,/*查询类型
                                        0：个人报表
                                        1：团队报表*/
            @Query("stype") int stype,
                                        /*帐变类型
                                        0：所有类型
                                        1：加入游戏
                                        2：投注返点
                                        3：奖金派送
                                        4：追号扣款
                                        5：当期追号返款
                                        6：游戏扣款
                                        7：撤单返款
                                        8：撤销返点
                                        9：撤销派奖*/
            @Query("model") int model,
                                            /*投注模式
                                            0：所有模式
                                            1：元
                                            2：角
                                            3：分
                                            4：厘*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );//注册

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/signup")
    Call<Object> getSignUp(
            @Query("AppClient") int num,
            @Query("u") String u,
            @Query("n") String n,
            @Query("p") String p,
            @Query("code") String code,
            @Query("c") String c,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //82 同步倒计时时间

    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/sync")
    Call<Map<String, Object>> getBettingSync(
            @Query("AppClient") int num,
            @Query("id") int id,

            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //52 个人报表活动记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-record-list")
    Call<Map<String, Object>> getActivityTeamRecordList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("name") String name,
            @Query("type") int type,/*查询类型
                                        0：所有类型
                                        22：活动奖金
                                        29：亏损佣金
                                        30：消费佣金*/
            @Query("team") int team,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //61获取推广设置数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-share-data")
    Call<Map<String, Object>> getShareData(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //62推广设置-保存
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/generalize-save")
    Call<Object> getGeneralizeSave(
            @Query("AppClient") int num,//推广的返点，数值在0-12.5之间
            @Query("l") double l,
            @Query("reqkey") String reqkey,
            @Query("t") int t/*推广的用户类型
                                2：推广用户类型为代理用户
                                3：推广用户类型为普通会员*/
    );

    //63 获取添加会员数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/adduser-data")
    Call<Object> getAddUserData(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //64 添加会员-保存
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/signup")
    Call<RestultInfo> getVIPSignUpSave(
            @Query("AppClient") int num,
            @Query("u") String u,//用户名
            @Query("n") String n,//昵称
            @Query("l") double l,//添加会员所选的返点
            @Query("code") String code,
            @Query("t") int t,/*
                                添加会员的类型；加密时需要放在最后
                                2：推广用户类型为代理用户
                                3：推广用户类型为普通会员
                                  */
            @Query("p") String p,//登录密码[需HmacSHA256加密]
            @Query("c") String c,//添加会员标识，这里是[addnewuser]
            @Query("reqkey") String reqkey

    );

    //65 会员统计
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/users-statistics")
    Call<Object> getUserStatistics(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //66 查询统计的用户信息
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/team-user-list")
    Call<Map<String, Object>> getTeamUserList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,/*排序参数，这里是[uid]*/
            @Query("sord") String sord,/*排序类型，这里是[desc]*/
            @Query("type") int type,/*查询的数据类型，这里是1*/
            @Query("stype") int stype,      /*
                                            查询数据的类别
                                            0：查询15天内未登录的用户
                                            1：查询今日注册的用户
                                            */
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //67 会员管理
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/team-user-list")
    Call<Map<String, Object>> getTeamUserInfo(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,/*排序参数，这里是[uid]*/
            @Query("sord") String sord,/*排序类型，这里是[desc]*/
            @Query("uid") int uid,//当前层级查询的最高用户uid；如默认查询就是当前登录的用户id，点击用户名查询下一级就是被点击用户的uid
            @Query("u") String u,//查询条件用户名，这里是精确查询
            /*@Query("b1") double b1,//查询余额范围的下限值
            @Query("b2") double b2,//查询余额范围的上限值
            @Query("l1") double l1,//查询返点范围的下限值
            @Query("l2") double l2,//查询返点范围的上限值*/
            @Query("t") int t,/*用户类型
                                0：所有用户
                                2：代理用户
                                3：普通会员
                                -1：在线用户*/
            @Query("reqkey") String reqkey
    );

    //68 获取会员管理层级数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/team-users-parent/{uid}")
    Call<Object> getTeamUsersParent(
            @Path("uid") int uid,
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //69 查询团队余额
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/team-balance-view")
    Call<Object> getTeamBalanceView(
            @Query("AppClient") int num,
            @Query("uid") int uid,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //70 获取设置返点的数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/urebate-data")
    Call<Object> getUreBateData(
            @Query("AppClient") int num,
            @Query("uid") int uid,//需要设置返点的用户id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //71 设置新返点
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/team-user-rebate-save")
    Call<RestultInfo> getTeamUserRebateSave(
            @Query("AppClient") int num,
            @Query("l") double l,//需要设置返点的用户id
            @Query("uid") int uid,//需要调整的用户id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //72 获取上级充值数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/srecharge-data")
    Call<Object> getSreChargeData(
            @Query("AppClient") int num,
            @Query("uid") int uid,//需要设置返点的用户id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //73 上级充值校验
    /*应用场景：上级充值或日工资充值需要验证安全问题答案和安全密码*/
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/own-transfer-check")
    Call<RestultInfo> getOwnReansferCheck(
            @Query("AppClient") int num,
            @Query("a") String a,//安全问题答案
            @Query("p") String p,//安全密码[需经过HmacSHA256加密]
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //74 保存上级充值
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/own-transfer-trans")
    Call<RestultInfo> getOwnReansferTrans(
            @Query("AppClient") int num,
            @Query("amount") double amount,//充值金额
            @Query("name") String name,//充值的用户名
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //75 保存日工资充值
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/daily-recharge-trans")
    Call<RestultInfo> getDailyRechargeTrans(
            @Query("AppClient") int num,
            @Query("amount") double amount,//充值金额
            @Query("name") String name,//充值的用户名
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //76 获取设置配额的数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/tquota-data")
    Call<Map<String, Object>> getTquotaData(
            @Query("AppClient") int num,
            @Query("uid") int uid,//调整配额的用户id
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //77 保存配额
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/team-user-quota-save")
    Call<RestultInfo> getTeamUserQuotaSave(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("uid") int uid,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("stype") int stype,/*帐变类型
                                        0：所有类型
                                        1：加入游戏
                                        2：投注返点
                                        3：奖金派送
                                        4：追号扣款
                                        5：当期追号返款
                                        6：游戏扣款
                                        7：撤单返款
                                        8：撤销返点
                                        9：撤销派奖
                                        10：上级充值
                                        11：充值扣费
                                        14：理赔充值
                                        16：提款申请
                                        17：提款失败
                                        18：提款成功
                                        19：在线充值
                                        20：现金充值
                                        21：充值手续费
                                        22：促销充值
                                        26：支付宝充值
                                        31：转账汇款*/
            @Query("model") int model,/*投注模式
                                        0：全部模式
                                        1：元
                                        2：角
                                        3：分
                                        4：厘*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //78 帐变记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/capital-change-list")
    Call<Map<String, Object>> getCapitalChangeList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("uid") int uid,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
            @Query("stype") int stype,
            @Query("model") int model,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //79 团队报表彩票投注
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-list")
    Call<Map<String, Object>> getTeamBettingList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("name") String name,
            @Query("status") int status,
                                    /*投注单状态
                                    -2：所有状态
                                    -1：未开奖
                                    0：未中奖
                                    1：已派奖
                                    2：本人撤单
                                    3：管理员撤单
                                    4：已过期
                                    6：平台撤单*/

            @Query("id") int id,
            @Query("rid") int rid,
            @Query("type") int type,
                                /*查询类型
                                0：个人查询
                                2：团队查询*/
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //80团队报表彩票盈亏  当日和历史  81
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/profit-loss-list")
    Call<Map<String, Object>> getTeamProfitLossList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("name") String name,
            @Query("uid") int uid,
            @Query("gtype") int gtype,
                            /*查询类型
                            1：彩票娱乐场
                            2：香港六合彩
                            0：所有类型*/
            @Query("stype") int stype,//查询数据类型，这里是[0]代表当日盈亏  1代表 历史
            @Query("team") int team,//个人/团队报表
            //0：个人
            //2：团队
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //85切换游戏/获取玩法数据(重点)
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/switch-game/{id}")
    Call<Map<String, Object>> getSwitchGameList(
            @Path("id") int id,
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    ); //86切换游戏/获取玩法数据(重点)

    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/betting/submit/{gid}")
    Call<Map<String, Object>> getSendBetting(
            @Path("gid") int gid,
            @Query("AppClient") int num,
            @Query("vcode1") String vcode1,
            @Query("ids") List<Map<String, Object>> ids,
            @Query("period") String period,
            @Query("array") List<Map<String, Object>> array,
            @Query("amount") double amount,
            @Query("stopByWin") int stopByWin,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //88更新返点
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/update-rate")
    Call<Object> getBettingUpdateRate(
            @Query("AppClient") int num,
            @Query("gid") int gid,
            @Query("rate") double rate,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //89更新奖金类型
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/update-price-type/{id}")
    Call<Object> getBettingUpdatePrice(
            @Path("id") int gid,//id 游戏gid
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //93获取开奖历史记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/draw-history/{id}")
    Call<List<Object>> getBettingDrawHistory(
            @Path("id") int gid,//id 游戏gid
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //90获取追号信息
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/betting/auto-purchase")
    Call<Object> getBettingAutoPurchase(
            @Query("AppClient") int num,
            @Query("id") int id,//id 游戏gid
            @Query("period") String period,//开始追号的期号
            @Query("periods") int periods,//追号期数
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //91获取全部追号的期数
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/auto-nums")
    Call<Object> getBettingAutoNums(
            @Query("AppClient") int num,
            @Query("id") int gid,//id 游戏gid
            @Query("period") String period,//开始追号的期号
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //92 同步上期开奖数据
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/winning-number")
    Call<Map<String, Object>> getBettingWinningNum(
            @Query("AppClient") int num,
            @Query("id") int gid,//id 游戏gid
            @Query("period") String period,//上一期倒计时时的期号
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //94投注记录
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/betting/betting-history/{id}")
    Call<Object> getBettingHistorys(
            @Path("id") int gid,//id 游戏gid
            @Query("AppClient") int num,
            @Query("page") int page,//当前分页数
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //95追号记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting/rebuy-history/{id}")
    Call<Object> getBettingRebuyHistorys(
            @Path("id") int gid,//id 游戏gid
            @Query("AppClient") int num,
            @Query("page") int page,//当前分页数
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //96 验证安全问题
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/security-question-check")
    Call<RestultInfo> getSecurityQuestionCheck(
            @Query("AppClient") int num,
            @Query("a") String a,//安全问题
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //97 验证安全问题
    @Headers("X-Requested-With: XMLHttpRequest")

    @POST("/token-signin")
    Call<Object> getTokenSignin(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

}
