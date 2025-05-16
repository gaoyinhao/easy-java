package com.easyjava.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
/**
 * @author gao98
 */
public class DateUtils {

    private static final Object lockObj = new Object();

    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = sdfMap.get(pattern);
        if (null == threadLocal) {
            synchronized (lockObj) {
                threadLocal = sdfMap.get(pattern);
                if (null == threadLocal) {
                    threadLocal = ThreadLocal.withInitial(new Supplier<SimpleDateFormat>() {
                        @Override
                        public SimpleDateFormat get() {
                            return new SimpleDateFormat(pattern);
                        }
                    });
                    sdfMap.put(pattern, threadLocal);
                }
            }
        }
        return threadLocal.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
