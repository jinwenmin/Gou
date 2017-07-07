package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/7.
 */

public class CunQu {
    int id;
    String serial_number;
    String uname;

    @Override
    public String toString() {
        return "CunQu{" +
                "id=" + id +
                ", serial_number='" + serial_number + '\'' +
                ", uname='" + uname + '\'' +
                ", date='" + date + '\'' +
                ", stype=" + stype +
                ", income=" + income +
                ", expend=" + expend +
                ", amount=" + amount +
                ", status=" + status +
                ", detial='" + detial + '\'' +
                ", incomes=" + incomes +
                ", expengs=" + expengs +
                ", serial_numbers='" + serial_numbers + '\'' +
                '}';
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    String date;
    int stype;
    double income;
    double expend;
    double amount;
    int status;
    String detial;
    double incomes;
    double expengs;
    String serial_numbers;

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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpend() {
        return expend;
    }

    public void setExpend(double expend) {
        this.expend = expend;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetial() {
        return detial;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    public double getExpengs() {
        return expengs;
    }

    public void setExpengs(double expengs) {
        this.expengs = expengs;
    }

    public String getSerial_numbers() {
        return serial_numbers;
    }

    public void setSerial_numbers(String serial_numbers) {
        this.serial_numbers = serial_numbers;
    }
}
