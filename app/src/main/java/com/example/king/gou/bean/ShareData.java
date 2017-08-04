package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ShareData {
    boolean checked;
    double mxrate;
    double rate;
    int rebate_id;
    String shareCode;
    String name;
    double Grate;
    double offset;
    int uid;

    @Override
    public String toString() {
        return "ShareData{" +
                "checked=" + checked +
                ", mxrate=" + mxrate +
                ", rate=" + rate +
                ", rebate_id=" + rebate_id +
                ", shareCode='" + shareCode + '\'' +
                ", name='" + name + '\'' +
                ", Grate=" + Grate +
                ", offset=" + offset +
                ", uid=" + uid +
                '}';
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getMxrate() {
        return mxrate;
    }

    public void setMxrate(double mxrate) {
        this.mxrate = mxrate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRebate_id() {
        return rebate_id;
    }

    public void setRebate_id(int rebate_id) {
        this.rebate_id = rebate_id;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrate() {
        return Grate;
    }

    public void setGrate(double grate) {
        Grate = grate;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
