package com.easyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gao98
 */
public class DateUtils {
    /**
     * yyyy_MM_dd
     */
    public static final String datePattern1 = "yyy-MM-dd";
    /**
     * yyyy/MM/dd
     */
    public static final String datePattern2 = "yyyy/MM/dd";

    /**
     * yyyyMMdd
     */
    public static final String datePattern3 = "yyyyMMdd";
    /**
     * yyyy-MM-dd HH:mm::ss
     */
    public static final String datePattern4 = "yyyy-MM-dd HH:mm::ss";

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
