package com.example.king.gou.bean;

/**
 * Created by king on 2017/8/27.
 */

public class Arrays {
    String period;
    int multiple;

    @Override
    public String toString() {
        return "Arrays{" +
                "period='" + period + '\'' +
                ", multiple=" + multiple +
                '}';
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }
}
