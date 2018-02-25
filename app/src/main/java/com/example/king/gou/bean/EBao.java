package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class EBao {
    boolean rc;
    String msg;
    int id;
    Others others;

    @Override
    public String toString() {
        return "EBao{" +
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
        List<Banks> banks;

        @Override
        public String toString() {
            return "Others{" +
                    "min=" + min +
                    ", max=" + max +
                    ", freeze=" + freeze +
                    ", banks=" + banks +
                    ", bank=" + bank +
                    '}';
        }

        public Banks getBank() {
            return bank;
        }

        public void setBank(Banks bank) {
            this.bank = bank;
        }

        Banks bank;

        public List<Banks> getBanks() {
            return banks;
        }

        public void setBanks(List<Banks> banks) {
            this.banks = banks;
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

        public  class Banks {
            String code;
            String name;

            @Override
            public String toString() {
                return "Banks{" +
                        "code='" + code + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
