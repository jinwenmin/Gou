package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/9/8.
 */

public class ZhuiHaoCNum {
    String period;
    String date;

    @Override
    public String toString() {
        return "ZhuiHaoCNum{" +
                "period='" + period + '\'' +
                ", date='" + date + '\'' +
                '}';
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
