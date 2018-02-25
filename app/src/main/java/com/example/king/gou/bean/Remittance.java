package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/17 0017.
 */

public class Remittance {
    boolean rc;
    String msg;
    int id;
    Others others;

    @Override
    public String toString() {
        return "Remittance{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", others=" + others +
                '}';
    }

    public Others getOthers() {
        return others;
    }

    public void setOthers(Others others) {
        this.others = others;
    }

    public boolean isRc() {
        return rc;
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class Others {
        double min;
        double max;
        boolean freeze;
        List<Remittance_Record> RR;

        @Override
        public String toString() {
            return "Others{" +
                    "min=" + min +
                    ", max=" + max +
                    ", freeze=" + freeze +
                    ", RR=" + RR +
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

        public List<Remittance_Record> getRR() {
            return RR;
        }

        public void setRR(List<Remittance_Record> RR) {
            this.RR = RR;
        }
    }
}
