package com.example.king.gou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/7.
 */

public class AccountChange implements Serializable {
    int id;
    String uname;
    String date;
    int stype;
    String gname;
    Double amountss;

    @Override
    public String toString() {
        return "AccountChange{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", date='" + date + '\'' +
                ", stype=" + stype +
                ", gname='" + gname + '\'' +
                ", amountss=" + amountss +
                ", rname='" + rname + '\'' +
                ", period='" + period + '\'' +
                ", model=" + model +
                ", amount=" + amount +
                ", amounts=" + amounts +
                '}';
    }

    public Double getAmountss() {
        return amountss;
    }

    public void setAmountss(Double amountss) {
        this.amountss = amountss;
    }

    String rname;
    String period;
    int model;
    double amount;
    double amounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
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

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmounts() {
        return amounts;
    }

    public void setAmounts(double amounts) {
        this.amounts = amounts;
    }
}
