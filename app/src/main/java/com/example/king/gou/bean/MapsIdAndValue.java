package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/29.
 */

public class MapsIdAndValue {
    int id;
    String Values;
    String Bank;
    String CardNum;
    String Time;
    String Locked;

    public String getLocked() {
        return Locked;
    }

    public void setLocked(String locked) {
        Locked = locked;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValues() {
        return Values;
    }

    public void setValues(String values) {
        Values = values;
    }

    @Override
    public String toString() {
        return "MapsIdAndValue{" +
                "id=" + id +
                ", Values='" + Values + '\'' +
                ", Bank='" + Bank + '\'' +
                ", CardNum='" + CardNum + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }
}
