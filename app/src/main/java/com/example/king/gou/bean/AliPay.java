package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/18 0018.
 */

public class AliPay {
    double min;
    double max;
    boolean freeze;
    Others others;

    @Override
    public String toString() {
        return "AliPay{" +
                "min=" + min +
                ", max=" + max +
                ", freeze=" + freeze +
                ", others=" + others +
                '}';
    }

    public Others getOthers() {
        return others;
    }

    public void setOthers(Others others) {
        this.others = others;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public class Others {
        double min;
        double max;
        boolean freeze;
        List<Remittance_Record> records;

        @Override
        public String toString() {
            return "Others{" +
                    "min=" + min +
                    ", max=" + max +
                    ", freeze=" + freeze +
                    ", records=" + records +
                    '}';
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public boolean isFreeze() {
            return freeze;
        }

        public void setFreeze(boolean freeze) {
            this.freeze = freeze;
        }

        public List<Remittance_Record> getRecords() {
            return records;
        }

        public void setRecords(List<Remittance_Record> records) {
            this.records = records;
        }
    }
}
