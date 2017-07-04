package com.example.king.gou.utils;

import java.util.Comparator;

class MapKeyComparator implements Comparator<String> {

	@Override
    public int compare(String str1, String str2) {
		
		return str1.compareTo(str2);
	}
}