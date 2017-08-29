package com.example.king.gou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/28.
 */

public class Ids implements Serializable {
    String pickedNumber;//投注内容，如果是单式选号，需要过滤掉不合法的投注内容
    String pickedText;//投注文本，大部分时候等于pickedNumber，只有有中文的投注，才会不一样，如果相同，提交的时候为了节省提交数据大小，可以传空字符串，单式加密的时候必须是原内容
    String location;//数字字符串，五个0或1组成，代表时时彩或分分彩任选玩法勾选的位置，0代表不勾选，1代表勾选
    String locationText;//由"万千百十个"五个字其中几个组成，代表勾选的位置的文本
    int num;//投注内容注数
    String classCode;//玩法编码
    int priceUnit;//投注模式
    //1：元；对应单注单价为￥2.000
    //    2：角；对应单注单价为￥0.200
    //  3：分；对应单注单价为￥0.020
    //  4：厘；对应单注单价为￥0.002
    int priceType;//奖金类型
    //0：最低奖
    //1：最高奖
    double amount;//投注金额；计算方法为：单价[2/0.2/0.02/0.002] * 注数[num] * 倍数[multiple]
    int multiple;//投注倍数
    double amounts;//总投注金额，大都等于amount
    int multiples;//投注总倍数，大都等于multiple
    String vcode;//每一单投注单加密秘钥
    //加密明文：
    // text16：16进制的随机6位拼接成的字符串
    //       vtext = ";" + pickedNumbers + ";" + pickedText + ";" + location + ";" + locationText + ";" + num + ";" + classCode+ ";" + priceUnit+ ";" + priceType + ";" + amount + ";" + multiple+ ";" + amounts + ";" + multiples;
    // vtext = 0 + vtext + text16
    // 加密方法为：SHA256单值加密
    //加密结果为：vcode = 0 + SHA256加密结果 + text16
    String gamename;

    @Override
    public String toString() {
        return "Ids{" +
                "pickedNumber='" + pickedNumber + '\'' +
                ", pickedText='" + pickedText + '\'' +
                ", location='" + location + '\'' +
                ", locationText='" + locationText + '\'' +
                ", num=" + num +
                ", classCode='" + classCode + '\'' +
                ", priceUnit=" + priceUnit +
                ", priceType=" + priceType +
                ", amount=" + amount +
                ", multiple=" + multiple +
                ", amounts=" + amounts +
                ", multiples=" + multiples +
                ", vcode='" + vcode + '\'' +
                ", gamename='" + gamename + '\'' +
                '}';
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getPickedNumber() {
        return pickedNumber;
    }

    public void setPickedNumber(String pickedNumber) {
        this.pickedNumber = pickedNumber;
    }

    public String getPickedText() {
        return pickedText;
    }

    public void setPickedText(String pickedText) {
        this.pickedText = pickedText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(int priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public double getAmounts() {
        return amounts;
    }

    public void setAmounts(double amounts) {
        this.amounts = amounts;
    }

    public int getMultiples() {
        return multiples;
    }

    public void setMultiples(int multiples) {
        this.multiples = multiples;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

}
