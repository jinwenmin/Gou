package com.example.king.gou.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10.
 */

public class SwitchGame {
    int gid=0;//游戏id
    int status=0;//游戏状态，不为1则游戏已暂停销售
    String gameName=null;//游戏名称
    String zodiacs=null;//生肖号码数据，仅当gid=28游戏为六合彩时，才返回该数据
    double rebate=0;//用户游戏返点，用于计算最高奖和显示最低奖投注返点
    double serverTime=0;//服务器当前时间时间戳，用于倒计时校准等
   // Map<String,Object> datas=new HashMap<>();//玩法数据
    String defaultGameRule=null;//默认玩法编码
    String howto=null;//默认玩法说明标签
    String example=null;//默认玩法[投注示例|开奖示例]
    String desc=null;//默认玩法说明
    int pricetype=0;//默认奖金类型
   // List<Object> priceTypes=new ArrayList<>();//默认玩法等级间隔系数，最低奖
    String property=null;//开奖号码性质
    String prePeriod=null;//开奖号码期号(上期开奖期号)
String winningNumber;//逗号分隔的开奖号码
    double amount;//账户余额
    String period;//当前期号
    int count;

    @Override
    public String toString() {
        return "SwitchGame{" +
                "gid=" + gid +
                ", status=" + status +
                ", gameName='" + gameName + '\'' +
                ", zodiacs='" + zodiacs + '\'' +
                ", rebate=" + rebate +
                ", serverTime=" + serverTime +
                ", defaultGameRule='" + defaultGameRule + '\'' +
                ", howto='" + howto + '\'' +
                ", example='" + example + '\'' +
                ", desc='" + desc + '\'' +
                ", pricetype=" + pricetype +
                ", property='" + property + '\'' +
                ", prePeriod='" + prePeriod + '\'' +
                ", winningNumber='" + winningNumber + '\'' +
                ", amount=" + amount +
                ", period='" + period + '\'' +
                ", count=" + count +
                ", rate=" + rate +
                ", tid=" + tid +
                ", rid=" + rid +
                ", name='" + name + '\'' +
                ", class_code='" + class_code + '\'' +
                ", minimum=" + minimum +
                ", coefficient=" + coefficient +
                ", dhowto='" + dhowto + '\'' +
                ", dnumber='" + dnumber + '\'' +
                ", dwinnums='" + dwinnums + '\'' +
                ", description='" + description + '\'' +
                ", grprize='" + grprize + '\'' +
                ", single_nums=" + single_nums +
                '}';
    }

    public String getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(String winningNumber) {
        this.winningNumber = winningNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    double rate;//游戏返点
    int tid;//玩法大分类id 玩法分类id
    int rid;//玩法id
    String name;//玩法名称
    String class_code;//玩法编码
    double minimum;//玩法最低奖/基础奖金[如果没有等级间隔系数或为0，或者没有游戏返点数据]
    double coefficient;//等级间隔系数，用于计算最高奖，如果为0则说明没有最高奖；最高奖计算方法：最高奖[maximum] = 最低奖[minimum] + 等级间隔系数[coefficient] * 游戏返点[rate];
    String dhowto;//玩法说明标签
    String dnumber;//投注示例
    String dwinnums;//开奖示例
    String description;//玩法说明
    String grprize;//对于minimum和coefficient都为0时，改参数就不为空，说明该玩法存在不同选号，奖金不同；拼接格式为：ptext:prize-rate<coefficient>
    int single_nums;//单挑模式对应的投注数，如果不为0，投注内容注数小于该值，就是单挑模式

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getZodiacs() {
        return zodiacs;
    }

    public void setZodiacs(String zodiacs) {
        this.zodiacs = zodiacs;
    }

    public double getRebate() {
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public double getServerTime() {
        return serverTime;
    }

    public void setServerTime(double serverTime) {
        this.serverTime = serverTime;
    }

    public String getDefaultGameRule() {
        return defaultGameRule;
    }

    public void setDefaultGameRule(String defaultGameRule) {
        this.defaultGameRule = defaultGameRule;
    }

    public String getHowto() {
        return howto;
    }

    public void setHowto(String howto) {
        this.howto = howto;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPricetype() {
        return pricetype;
    }

    public void setPricetype(int pricetype) {
        this.pricetype = pricetype;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPrePeriod() {
        return prePeriod;
    }

    public void setPrePeriod(String prePeriod) {
        this.prePeriod = prePeriod;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getDhowto() {
        return dhowto;
    }

    public void setDhowto(String dhowto) {
        this.dhowto = dhowto;
    }

    public String getDnumber() {
        return dnumber;
    }

    public void setDnumber(String dnumber) {
        this.dnumber = dnumber;
    }

    public String getDwinnums() {
        return dwinnums;
    }

    public void setDwinnums(String dwinnums) {
        this.dwinnums = dwinnums;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrprize() {
        return grprize;
    }

    public void setGrprize(String grprize) {
        this.grprize = grprize;
    }

    public int getSingle_nums() {
        return single_nums;
    }

    public void setSingle_nums(int single_nums) {
        this.single_nums = single_nums;
    }
}
