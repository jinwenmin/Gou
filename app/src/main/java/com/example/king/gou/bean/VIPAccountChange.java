package com.example.king.gou.bean;

/**
 * Created by king on 2017/8/3.
 */

public class VIPAccountChange {
    int atid;
    String serial_number;
    String draw_period;
    String time;
    String game;
    int type;
    int price_unit;
    double amount;
    double balance;
    String remark;

    @Override
    public String toString() {
        return "VIPAccountChange{" +
                "atid=" + atid +
                ", serial_number='" + serial_number + '\'' +
                ", draw_period='" + draw_period + '\'' +
                ", time='" + time + '\'' +
                ", game='" + game + '\'' +
                ", type=" + type +
                ", price_unit=" + price_unit +
                ", amount=" + amount +
                ", balance=" + balance +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getAtid() {
        return atid;
    }

    public void setAtid(int atid) {
        this.atid = atid;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getDraw_period() {
        return draw_period;
    }

    public void setDraw_period(String draw_period) {
        this.draw_period = draw_period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(int price_unit) {
        this.price_unit = price_unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
