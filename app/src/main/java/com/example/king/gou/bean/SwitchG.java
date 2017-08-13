package com.example.king.gou.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2017/8/12.
 */

public class SwitchG {
    Object id1;
    Object name1;
    List<SwitchGa> switchGas;

    @Override
    public String toString() {
        return "SwitchG{" +
                "id1=" + id1 +
                ", name1=" + name1 +
                '}';
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
        List<SwitchGam> switchGams;

        @Override
        public String toString() {
            return "SwitchGa{" +
                    "id2=" + id2 +
                    ", name2=" + name2 +
                    '}';
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

      public static  class SwitchGam {
            Object id3;
            Object name3;

          @Override
          public String toString() {
              return "SwitchGam{" +
                      "id3=" + id3 +
                      ", name3=" + name3 +
                      '}';
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
