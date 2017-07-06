package com.example.king.gou.utils;

import android.text.TextUtils;

import com.example.king.gou.bean.Login;
import com.example.king.gou.bean.LoginState;
import com.example.king.gou.bean.NoticeContent;
import com.example.king.gou.bean.RestultInfo;

import com.example.king.gou.bean.UserAmount;
import com.example.king.gou.bean.UserInfo;

import java.math.BigDecimal;
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

    //登出有日志
    @Headers("X-Requested-With: XMLHttpRequest")
    @GET("/logout")
    Call<Object> getSignout(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //登陆状态查询
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/chat-message")
    Call<Object> getLoginState(

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
    Call<Object> getActivityList(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-notices")
    Call<Object> getNotices(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t

    );

    //公告列表
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/notice-view/{id}")
    Call<Object> getNoticesContent(

            @Path("id") int id

    );

    //获取玩法
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-rules_description/{tid}")
    Call<Object> getGameType(@Path("tid") int tid);

    //获取游戏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-game-datas")
    Call<Object> getGame(
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
    Call<Object> getPrizeDetails(@Query("rows") int rows,
                                 @Query("page") int page,
                                 @Query("sidx") String sidx,
                                 @Query("sord") String sord,
                                 @Query("id") int id,
                                 @Query("rid") int rid
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
    Call<Object> getActivityCheck(@Query("id") int id,//活动id
                                  @Query("alid") int alid//活动层级id

    );

    //用户信息页面修改昵称
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
            @Query("p0") String p1,//新密码
            @Query("p0") String p2,//安全密码
            @Query("email") String email,//邮箱
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取安全问题
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/get-questions")
    Call<Object> getSafeQues(
            @Query("AppClient") int num,
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //获取首页的公告或者滚动栏
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/notice-view-pop")
    Call<Object> getHomeNotice();

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
    Call<Object> getCardDatas(
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
    Call<Object> getPrivens(
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
    Call<Object> getWithDrawData(
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
    @POST("/password-manage-save")
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
    Call<Object> getChatList(

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
    @POST("/message-manage-loads")
    Call<Object> getSendMsg(
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
    Call<Object> getNewMsg(
            @Query("AppClient") int num,
            @Query("id") int id,//聊天对象uid
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );


    //投注单详情
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-details/{id}")
    Call<Object> getBettingDetails(
            @Query("AppClient") int num,
            @Path("id") int id,//id为投注单bid
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //查询投注记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/betting-record-list")
    Call<Object> getBettingRecordList(
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
    Call<RestultInfo> getLotteryBetRevoke(
            @Query("AppClient") int num,
            @Path("bid") int id,//购彩单id
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
            @Path("id") int id//id为追号记录id

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
    Call<Object> getLotteryBetRevoke(
            @Query("AppClient") int num,
            @Query("ids") String ids,//勾选的投注单id集；逗号分隔
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //50 查询个人报表彩票帐变
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/account-change-list")
    Call<Object> getAccountChangeList(
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

    //51 个人报表充提记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/activity-record-list")
    Call<Object> getActivityRecordList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,

            @Query("type") int type,/*查询类型
                                        0：所有类型
                                        10：[+]上级充值
                                        11：[-]充值扣费
                                        12：[-]小额扣费
                                        13：特殊金额整理
                                        14：[+]理赔充值
                                        15：[-]管理员扣减
                                        16：[-]提款申请
                                        17：[+]提款失败
                                        18：[=]提款成功
                                        19：[+]在线充值
                                        20：[+]现金充值
                                        21：[+]充值手续费
                                        22：[+]活动奖金
                                        26：[+]支付宝充值
                                        31：[+]转账汇款
                                        32：[+]日工资
                                        33：[-]日工资扣费
                                        34：[+]日工资充值*/

            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

    //52 个人报表活动记录
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("/recharge-withdraw-list")
    Call<Object> getReChargeWithDrawList(
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
            @Query("status") String status,
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
    Call<Object> getProfitLossList(
            @Query("AppClient") int num,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("sidx") String sidx,
            @Query("sord") String sord,
            @Query("from") String from,
            @Query("to") String to,
            @Query("id") int id,
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
    @POST("/get-data?id={id}")
    Call<Object> getGetMoreData(
            @Query("AppClient") int num,
            @Path("id") int id,//id为投注单bid
            @Query("reqkey") String reqkey,
            @Query("t") long t
    );

}
