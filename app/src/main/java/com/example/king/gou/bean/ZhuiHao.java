package com.example.king.gou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ZhuiHao implements Serializable{
    int id;
    String number;
    String purchase_date;
    String gname;
    String rname;
    String start_period;
    int periods;
    int purchase_periods;
    String picked_numbers;
    int mode;
    double amount;
    double purchase_amount;
    double cancel_amount;
    int prize_num;
    double prize_amount;
    String bids;
    int status;

    @Override
    public String toString() {
        return "ZhuiHao{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", purchase_date='" + purchase_date + '\'' +
                ", gname='" + gname + '\'' +
                ", rname='" + rname + '\'' +
                ", start_period='" + start_period + '\'' +
                ", periods=" + periods +
                ", purchase_periods=" + purchase_periods +
                ", picked_numbers='" + picked_numbers + '\'' +
                ", mode=" + mode +
                ", amount=" + amount +
                ", purchase_amount=" + purchase_amount +
                ", cancel_amount=" + cancel_amount +
                ", prize_num=" + prize_num +
                ", prize_amount=" + prize_amount +
                ", bids='" + bids + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
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

    public String getStart_period() {
        return start_period;
    }

    public void setStart_period(String start_period) {
        this.start_period = start_period;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getPurchase_periods() {
        return purchase_periods;
    }

    public void setPurchase_periods(int purchase_periods) {
        this.purchase_periods = purchase_periods;
    }

    public String getPicked_numbers() {
        return picked_numbers;
    }

    public void setPicked_numbers(String picked_numbers) {
        this.picked_numbers = picked_numbers;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(double purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public double getCancel_amount() {
        return cancel_amount;
    }

    public void setCancel_amount(double cancel_amount) {
        this.cancel_amount = cancel_amount;
    }

    public int getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(int prize_num) {
        this.prize_num = prize_num;
    }

    public double getPrize_amount() {
        return prize_amount;
    }

    public void setPrize_amount(double prize_amount) {
        this.prize_amount = prize_amount;
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
