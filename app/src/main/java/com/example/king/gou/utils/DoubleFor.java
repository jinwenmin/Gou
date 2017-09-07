package com.example.king.gou.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleFor {
    String str = "万千百十个";
    StringBuilder stringBuilder = new StringBuilder();


    public static void main(String[] args) {
        DoubleFor doubleFor = new DoubleFor();
        doubleFor.getName(doubleFor.str);
    }

    public void getName(String string) {
        int z, y;
        int length = string.length();
        for (int i = 0; i < length; i++) {
            string.charAt(i);
//            System.out.println(string.charAt(i));
            String s = String.valueOf(string.charAt(i));
            for (int j = i + 1; j < length; j++) {
//                stringBuilder.append(string.charAt(j));
                String s1 = String.valueOf(string.charAt(j));
//                System.out.println(""+s+s1);
                for (z = j + 1; z < length; z++) {
                    String s2 = String.valueOf(string.charAt(z));
//                    System.out.println(""+s+s1+s2);
                    for (y = z + 1; y < length; y++) {
                        String s3 = String.valueOf(string.charAt(y));
                        System.out.println("" + s + s1 + s2 + s3);
                    }
                }
            }

        }
    }
}