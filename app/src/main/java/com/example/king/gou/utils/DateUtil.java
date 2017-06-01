package com.example.king.gou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/1.
 */

public class DateUtil {
    static DateUtil dateUtil;

    public synchronized static DateUtil getInstance() {
        if (dateUtil == null) {
            dateUtil = new DateUtil();
        } else {
            return dateUtil;

        }
        return dateUtil;
    }

    public static long getdaytime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dt2.getTime();
    }
    // 从字符串, 获取日期, 如time = "2016-3-16 4:12:16"
    public static Long toDate(String time)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);

            return date.getTime();
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
