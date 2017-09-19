package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/9/19.
 */

public class Rates {
    double minimum;
    double coefficient;
    double rate;

    @Override
    public String toString() {
        return "Rates{" +
                "minimum=" + minimum +
                ", coefficient=" + coefficient +
                ", rate=" + rate +
                '}';
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
