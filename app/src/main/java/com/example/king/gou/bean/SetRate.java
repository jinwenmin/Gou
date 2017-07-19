package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/19.
 */

public class SetRate {
    double mxrate;
    double mnrate;

    @Override
    public String toString() {
        return "SetRate{" +
                "mxrate=" + mxrate +
                ", mnrate=" + mnrate +
                '}';
    }

    public double getMxrate() {
        return mxrate;
    }

    public void setMxrate(double mxrate) {
        this.mxrate = mxrate;
    }

    public double getMnrate() {
        return mnrate;
    }

    public void setMnrate(double mnrate) {
        this.mnrate = mnrate;
    }
}
