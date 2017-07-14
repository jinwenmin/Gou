package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/14.
 */

public class UserTeamBetting {
    int bid;
    int uid;
    String uname;
    String date;
    String gname;
    String period;
    String rname;
    String picked_text;
    double amount;
    double prize;
    String winning_number;
    int status;

    @Override
    public String toString() {
        return "UserTeamBetting{" +
                "bid=" + bid +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", date='" + date + '\'' +
                ", gname='" + gname + '\'' +
                ", period='" + period + '\'' +
                ", rname='" + rname + '\'' +
                ", picked_text='" + picked_text + '\'' +
                ", amount=" + amount +
                ", prize=" + prize +
                ", winning_number='" + winning_number + '\'' +
                ", status=" + status +
                '}';
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getPicked_text() {
        return picked_text;
    }

    public void setPicked_text(String picked_text) {
        this.picked_text = picked_text;
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

    public String getWinning_number() {
        return winning_number;
    }

    public void setWinning_number(String winning_number) {
        this.winning_number = winning_number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
