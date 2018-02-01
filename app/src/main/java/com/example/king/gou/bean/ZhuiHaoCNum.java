package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/9/8.
 */

public class ZhuiHaoCNum {
    String period;
    String date;
    int bei;
    double amounts;
    boolean isCheck;

    @Override
    public String toString() {
        return "ZhuiHaoCNum{" +
                "period='" + period + '\'' +
                ", date='" + date + '\'' +
                ", bei=" + bei +
                ", amounts=" + amounts +
                ", isCheck=" + isCheck +
                '}';
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getBei() {
        return bei;
    }

    public void setBei(int bei) {
        this.bei = bei;
    }

    public double getAmounts() {
        return amounts;
    }

    public void setAmounts(double amounts) {
        this.amounts = amounts;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
