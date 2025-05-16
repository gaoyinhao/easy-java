package com.easyjava.constant;

import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;

/**
 * @author gao98
 */
public class Constants {

    /**
     * 是否忽略表前缀
     */
    public static Boolean IGNORE_TABLE_PREFIX;
    /**
     * 自定义查询实体类的类名后缀
     */
    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_MAPPERS;

    public static String AUTHOR_COMMENT;

    private static String PATH_RESOURCES = "resources";
    private static String PATH_JAVA = "java";


    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_QUERY;
    public static String PATH_ENUMS;
    public static String PATH_DATE_UTILS;
    public static String PATH_MAPPERS_XML;
    public static String PATH_MAPPERS;
    public static String PATH_SERVICE;
    public static String PATH_SERVICE_IMPL;
    public static String PATH_EXCEPTION;
    public static String PATH_CONTROLLER;
    public static String PATH_VO;


    public static String PACKAGE_QUERY;
    public static String PACKAGE_BASE;
    public static String PACKAGE_PO;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;
    public static String PACKAGE_MAPPERS;
    public static String PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL;
    public static String PACKAGE_CONTROLLER;
    public static String PACKAGE_EXCEPTION;
    public static String PACKAGE_VO;


    /**
     * 需要忽略的属性
     */
    public static String IGNORE_BEAN_TO_JSON_FIELD;
    public static String IGNORE_BEAN_TO_JSON_EXPRESSION;
    public static String IGNORE_BEAN_TO_JSON_CLASS;
    /**
     * 日期格式序列化
     */
    public static String BEAN_DATE_JSON_FORMAT_EXPRESSION;
    public static String BEAN_DATE_JSON_FORMAT_CLASS;
    /**
     * 日期格式反序列化
     */
    public static String BEAN_DATE_JSON_UN_FORMAT_EXPRESSION;
    public static String BEAN_DATE_JSON_UN_FORMAT_CLASS;
    /**
     * po模糊搜索相关变量
     */
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;


    public static void main(String[] args) {
        System.out.println(PATH_MAPPERS_XML);
    }

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));

        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_MAPPERS = PropertiesUtils.getString("suffix.mappers");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");

        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");

        //com.easyjava
        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        //com.easyjava.entity.po
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_MAPPERS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mappers");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
        PACKAGE_SERVICE_IMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.vo");
        PACKAGE_EXCEPTION = PACKAGE_BASE + "." + PropertiesUtils.getString("package.exception");
        PACKAGE_CONTROLLER = PACKAGE_BASE + "." + PropertiesUtils.getString("package.controller");

        //C:/Program Files/IdeaProjects/workspace-easyjava/easyjava-demo/src/main/
        PATH_BASE = PropertiesUtils.getString("path.base");
        //C:/Program Files/IdeaProjects/workspace-easyjava/easyjava-demo/src/main/java
        PATH_BASE = PATH_BASE + PATH_JAVA;
        //C:/Program Files/IdeaProjects/workspace-easyjava/easyjava-demo/src/main/java/com/easyjava/entity/po
        PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");
        //C:/Program Files/IdeaProjects/workspace-easyjava/easyjava-demo/src/main/java/com/easyjava/entity/query
        PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(".", "/");
        //C:/Program Files/IdeaProjects/workspace-easyjava/easyjava-demo/src/main/java/com/easyjava/utils
        PATH_DATE_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(".", "/");
        PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(".", "/");
        PATH_MAPPERS = PATH_BASE + "/" + PACKAGE_MAPPERS.replace(".", "/");
        PATH_MAPPERS_XML = PropertiesUtils.getString("path.base") + PATH_RESOURCES + "/" + PACKAGE_MAPPERS.replace(".", "/");
        PATH_SERVICE = PATH_BASE + "/" + PACKAGE_SERVICE.replace(".", "/");
        PATH_SERVICE_IMPL = PATH_BASE + "/" + PACKAGE_SERVICE_IMPL.replace(".", "/");
        PATH_VO = PATH_BASE + "/" + PACKAGE_VO.replace(".", "/");
        PATH_EXCEPTION = PATH_BASE + "/" + PACKAGE_EXCEPTION.replace(".", "/");
        PATH_CONTROLLER = PATH_BASE + "/" + PACKAGE_CONTROLLER.replace(".", "/");

        /**
         * 需要忽略的属性
         */
        IGNORE_BEAN_TO_JSON_FIELD = PropertiesUtils.getString("ignore.bean.toJson.field");
        IGNORE_BEAN_TO_JSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.toJson.expression");
        IGNORE_BEAN_TO_JSON_CLASS = PropertiesUtils.getString("ignore.bean.toJson.class");
        /**
         * 日期格式序列化
         */
        BEAN_DATE_JSON_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.json.format.expression");
        BEAN_DATE_JSON_FORMAT_CLASS = PropertiesUtils.getString("bean.date.json.format.class");
        /**
         * 日期格式反序列化
         */
        BEAN_DATE_JSON_UN_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.json.unFormat.expression");
        BEAN_DATE_JSON_UN_FORMAT_CLASS = PropertiesUtils.getString("bean.date.json.unFormat.class");
    }

    /**
     * TimeStamp
     */
    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    /**
     * Date
     */
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    /**
     * float
     */
    public final static String[] SQL_DECIMAL_TYPES = new String[]{"decimal", "double", "float"};
    /**
     * String
     */
    public final static String[] SQL_STRING_TYPES = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    /**
     * Integer
     */
    public final static String[] SQL_INTEGER_TYPES = new String[]{"int", "tinyint"};
    /**
     * Long
     */
    public final static String[] SQL_LONG_TYPES = new String[]{"bigint"};


}
