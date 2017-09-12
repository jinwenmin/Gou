package com.example.king.gou.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2017/8/12.
 */

public class SwitchG {
    Object id1;
    Object name1;
    String class_code1;
    List<SwitchGa> switchGas;
    double rate;

    @Override
    public String toString() {
        return "SwitchG{" +
                "id1=" + id1 +
                ", name1=" + name1 +
                ", class_code1='" + class_code1 + '\'' +
                ", switchGas=" + switchGas +
                ", rate=" + rate +
                '}';
    }

    public void setClass_code1(String class_code1) {
        this.class_code1 = class_code1;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getClass_code1() {
        return class_code1;
    }

    public void setClass_code(String class_code1) {
        this.class_code1 = class_code1;
    }


    public List<SwitchGa> getSwitchGas() {
        return switchGas;
    }

    public void setSwitchGas(List<SwitchGa> switchGas) {
        this.switchGas = switchGas;
    }

    public Object getId1() {

        return id1;
    }

    public void setId1(Object id1) {
        this.id1 = id1;
    }

    public Object getName1() {
        return name1;
    }

    public void setName1(Object name1) {
        this.name1 = name1;
    }


    public static class SwitchGa {
        Object id2;
        Object name2;
        String class_code2;
        double minimum2;
        double coefficient2;
        List<SwitchGam> switchGams;

        @Override
        public String toString() {
            return "SwitchGa{" +
                    "id2=" + id2 +
                    ", name2=" + name2 +
                    ", class_code2='" + class_code2 + '\'' +
                    ", minimum2=" + minimum2 +
                    ", coefficient2=" + coefficient2 +
                    ", switchGams=" + switchGams +
                    ", rate2=" + rate2 +
                    '}';
        }

        public double getRate2() {
            return rate2;
        }

        public void setRate2(double rate2) {
            this.rate2 = rate2;
        }

        double rate2;

        public double getMinimum2() {
            return minimum2;
        }

        public void setMinimum2(double minimum2) {
            this.minimum2 = minimum2;
        }

        public double getCoefficient2() {
            return coefficient2;
        }

        public void setCoefficient2(double coefficient2) {
            this.coefficient2 = coefficient2;
        }

        public String getClass_code2() {
            return class_code2;
        }

        public void setClass_code2(String class_code2) {
            this.class_code2 = class_code2;
        }

        public Object getId2() {
            return id2;
        }

        public void setId2(Object id2) {
            this.id2 = id2;
        }

        public Object getName2() {
            return name2;
        }

        public void setName2(Object name2) {
            this.name2 = name2;
        }

        public List<SwitchGam> getSwitchGams() {
            return switchGams;
        }

        public void setSwitchGams(List<SwitchGam> switchGams) {
            this.switchGams = switchGams;
        }

        public static class SwitchGam {
            Object id3;
            Object name3;
            String class_code3;
            double minimum3;
            double coefficient3;
            double rate3;

            @Override
            public String toString() {
                return "SwitchGam{" +
                        "id3=" + id3 +
                        ", name3=" + name3 +
                        ", class_code3='" + class_code3 + '\'' +
                        ", minimum3=" + minimum3 +
                        ", coefficient3=" + coefficient3 +
                        ", rate=" + rate3 +
                        '}';
            }

            public double getRate3() {
                return rate3;
            }

            public void setRate3(double rate3) {
                this.rate3 = rate3;
            }

            public double getMinimum3() {
                return minimum3;
            }

            public void setMinimum3(double minimum3) {
                this.minimum3 = minimum3;
            }

            public double getCoefficient3() {
                return coefficient3;
            }

            public void setCoefficient3(double coefficient3) {
                this.coefficient3 = coefficient3;
            }

            public String getClass_code3() {
                return class_code3;
            }

            public void setClass_code3(String class_code3) {
                this.class_code3 = class_code3;
            }

            public Object getId3() {
                return id3;
            }

            public void setId3(Object id3) {
                this.id3 = id3;
            }

            public Object getName3() {
                return name3;
            }

            public void setName3(Object name3) {
                this.name3 = name3;
            }
        }
    }

}
