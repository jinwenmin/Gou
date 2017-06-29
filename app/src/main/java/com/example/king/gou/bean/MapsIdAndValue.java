package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/29.
 */

public class MapsIdAndValue {
    int id;
    String Values;


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
                '}';
    }
}
