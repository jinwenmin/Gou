package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class BettingDetail {
    boolean rc;
    int id;
    String serial_number;
    String uname;
    String buy_time;
    String gname;
    String rname;
    String period;
    String picked_text;
    int multiple;
    int price_unit;
    double amount;
    int status;
    int bid;
    int bstatus;
    int price_type;
    double minimum;
    double coefficient;
    double rate;
    int num;
    String class_code;
    String uid;

    @Override
    public String toString() {
        return "BettingDetail{" +
                "rc=" + rc +
                ", id=" + id +
                ", serial_number='" + serial_number + '\'' +
                ", uname='" + uname + '\'' +
                ", buy_time='" + buy_time + '\'' +
                ", gname='" + gname + '\'' +
                ", rname='" + rname + '\'' +
                ", period='" + period + '\'' +
                ", picked_text='" + picked_text + '\'' +
                ", multiple=" + multiple +
                ", price_unit=" + price_unit +
                ", amount=" + amount +
                ", status=" + status +
                ", bid=" + bid +
                ", bstatus=" + bstatus +
                ", price_type=" + price_type +
                ", minimum=" + minimum +
                ", coefficient=" + coefficient +
                ", rate=" + rate +
                ", num=" + num +
                ", class_code='" + class_code + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public boolean isRc() {
        return rc;
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPicked_text() {
        return picked_text;
    }

    public void setPicked_text(String picked_text) {
        this.picked_text = picked_text;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(int price_unit) {
        this.price_unit = price_unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getBstatus() {
        return bstatus;
    }

    public void setBstatus(int bstatus) {
        this.bstatus = bstatus;
    }

    public int getPrice_type() {
        return price_type;
    }

    public void setPrice_type(int price_type) {
        this.price_type = price_type;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
