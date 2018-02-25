package com.example.king.gou.bean;

import android.content.Intent;

/**
 * Created by Administrator on 2018/2/17 0017.
 */

public class Remittance_Record {
    int id;
    String datetime;
    Integer amount;
    String note;
    int status;
    String remark;

    @Override
    public String toString() {
        return "Remittance_Record{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
