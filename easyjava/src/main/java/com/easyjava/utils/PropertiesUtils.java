package com.easyjava.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gao98
 * 读取application.properties文件里的key-value，存储到map中供后续其他地方使用
 */
public class PropertiesUtils {
    private static Properties props = new Properties();
    private static Map<String, String> PROPER_MAP = new ConcurrentHashMap<String, String>();

    static {
        InputStream inputStream = null;
        try {
            inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(new InputStreamReader(inputStream, "gbk"));
            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                PROPER_MAP.put(key, props.getProperty(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String getString(String key) {
        return PROPER_MAP.get(key);
    }
}
