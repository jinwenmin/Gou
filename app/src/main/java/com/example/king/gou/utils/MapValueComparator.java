package com.example.king.gou.utils;

import java.util.Comparator;
import java.util.Map;

class MapValueComparator implements Comparator<Map.Entry<String, String>> {

    @Override
    public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}