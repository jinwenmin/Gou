package com.example.king.gou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/7/5.
 */

public class TouZhu {
    int bid;
    String name;
    String rname;
    String period;
    String piced_text;
    double amount;
    double prize;
    String re_buy;
    String buy_time;
    int status;
    String winning_numbers;



    @Override
    public String toString() {
        return "TouZhu{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", rname='" + rname + '\'' +
                ", period='" + period + '\'' +
                ", piced_text='" + piced_text + '\'' +
                ", amount=" + amount +
                ", prize=" + prize +
                ", re_buy='" + re_buy + '\'' +
                ", buy_time='" + buy_time + '\'' +
                ", status=" + status +
                ", winning_numbers='" + winning_numbers + '\'' +
                '}';
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPiced_text() {
        return piced_text;
    }

    public void setPiced_text(String piced_text) {
        this.piced_text = piced_text;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public String getRe_buy() {
        return re_buy;
    }

    public void setRe_buy(String re_buy) {
        this.re_buy = re_buy;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWinning_numbers() {
        return winning_numbers;
    }

    public void setWinning_numbers(String winning_numbers) {
        this.winning_numbers = winning_numbers;
    }


}
