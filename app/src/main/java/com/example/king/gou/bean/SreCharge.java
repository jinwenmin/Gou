package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/19.
 */

public class SreCharge {
    String ruser;
    String username;
    boolean haspass;
    boolean hassqas;
    String q;
    double amounts1;
    double min1;
    double max1;
    double amounts2;
    double min2;
    double max2;
    int stype;
    boolean dtype;

    @Override
    public String toString() {
        return "SreCharge{" +
                "ruser='" + ruser + '\'' +
                ", username='" + username + '\'' +
                ", haspass=" + haspass +
                ", hassqas=" + hassqas +
                ", q='" + q + '\'' +
                ", amounts1=" + amounts1 +
                ", min1=" + min1 +
                ", max1=" + max1 +
                ", amounts2=" + amounts2 +
                ", min2=" + min2 +
                ", max2=" + max2 +
                ", stype=" + stype +
                ", dtype=" + dtype +
                '}';
    }

    public String getRuser() {
        return ruser;
    }

    public void setRuser(String ruser) {
        this.ruser = ruser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHaspass() {
        return haspass;
    }

    public void setHaspass(boolean haspass) {
        this.haspass = haspass;
    }

    public boolean isHassqas() {
        return hassqas;
    }

    public void setHassqas(boolean hassqas) {
        this.hassqas = hassqas;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public double getAmounts1() {
        return amounts1;
    }

    public void setAmounts1(double amounts1) {
        this.amounts1 = amounts1;
    }

    public double getMin1() {
        return min1;
    }

    public void setMin1(double min1) {
        this.min1 = min1;
    }

    public double getMax1() {
        return max1;
    }

    public void setMax1(double max1) {
        this.max1 = max1;
    }

    public double getAmounts2() {
        return amounts2;
    }

    public void setAmounts2(double amounts2) {
        this.amounts2 = amounts2;
    }

    public double getMin2() {
        return min2;
    }

    public void setMin2(double min2) {
        this.min2 = min2;
    }

    public double getMax2() {
        return max2;
    }

    public void setMax2(double max2) {
        this.max2 = max2;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public boolean isDtype() {
        return dtype;
    }

    public void setDtype(boolean dtype) {
        this.dtype = dtype;
    }
}
