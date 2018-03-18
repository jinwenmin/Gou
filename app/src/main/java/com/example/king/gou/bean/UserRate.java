package com.example.king.gou.bean;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class UserRate {
    String Rate;
    String RateNum;

    @Override
    public String toString() {
        return "UserRate{" +
                "Rate='" + Rate + '\'' +
                ", RateNum='" + RateNum + '\'' +
                '}';
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getRateNum() {
        return RateNum;
    }

    public void setRateNum(String rateNum) {
        RateNum = rateNum;
    }
}
