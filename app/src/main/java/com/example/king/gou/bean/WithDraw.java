package com.example.king.gou.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/7/24.
 */

public class WithDraw {
    boolean freeze;
    boolean notime;
    String start;
    String end;
    int nums;
    BigDecimal amounts;
    int aid;
    String cardNumber;
    String holders_name;

    @Override
    public String toString() {
        return "WithDraw{" +
                "freeze=" + freeze +
                ", notime=" + notime +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", nums=" + nums +
                ", amounts=" + amounts +
                ", aid=" + aid +
                ", cardNumber='" + cardNumber + '\'' +
                ", holders_name='" + holders_name + '\'' +
                '}';
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isNotime() {
        return notime;
    }

    public void setNotime(boolean notime) {
        this.notime = notime;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal amounts) {
        this.amounts = amounts;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolders_name() {
        return holders_name;
    }

    public void setHolders_name(String holders_name) {
        this.holders_name = holders_name;
    }
}
