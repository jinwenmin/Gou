package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ActivityBean {
    int id;
    String serial_number;
    String uname;
    String date;
    int stype;
    double amount;
    double amounts;
    String detial;
    double samount;

    @Override
    public String toString() {
        return "ActivityBean{" +
                "id=" + id +
                ", serial_number='" + serial_number + '\'' +
                ", uname='" + uname + '\'' +
                ", date='" + date + '\'' +
                ", stype=" + stype +
                ", amount=" + amount +
                ", amounts=" + amounts +
                ", detial='" + detial + '\'' +
                ", samount=" + samount +
                '}';
    }

    public double getSamount() {
        return samount;
    }

    public void setSamount(double samount) {
        this.samount = samount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
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

    public String getDetial() {
        return detial;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }
}
