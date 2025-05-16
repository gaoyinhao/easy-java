package com.easyjava.builder;

import com.easyjava.constant.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.JsonUtils;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 构造表
 *
 * @author gao98
 */
public class BuildTable {

    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;

    private static String SQL_SHOW_TABLE_STATUS = "show table status";

    private static String SQL_SHOW_TABLE_FIELDS_FROM = "show full fields from %s";

    private static String SQL_SHOW_INDEX_FROM = "show index from %s";

    /**
     * static静态代码块，在编译时候已经执行里面的代码进行JDBC连接
     */
    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String username = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    /**
     * 获取表的所有信息
     */
    public static List<TableInfo> getTables() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;
        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()) {
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");
                String beanName = tableName;
                if (Constants.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(tableName.indexOf("_") + 1);
                }
                beanName = processFieldName(beanName, true);

                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setBeanParamName(beanName + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY));
                //读取该表的属性信息
                getFieldInfo(tableInfo);
                //获取该表的的索引集合
                getKeyIndexInfo(tableInfo);
                //将表加入到表list中
                tableInfoList.add(tableInfo);
            }
        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            closeResultSet(tableResult);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return tableInfoList;
    }

    /**
     * 读取每张表的属性信息
     *
     * @param tableInfo
     * @return
     */
    public static void getFieldInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;
        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
        List<FieldInfo> fieldExtendList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS_FROM, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            Boolean haveDateTime = false;
            Boolean haveDate = false;
            Boolean haveBigDecimal = false;
            while (fieldResult.next()) {
                String fieldName = fieldResult.getString("field");
                String sqlType = fieldResult.getString("type");
                String extraInfo = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");
                if (sqlType.indexOf("(") > 0) {
                    sqlType = sqlType.substring(0, sqlType.indexOf("("));
                }
                String propertyName = processFieldName(fieldName, false);
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(fieldName);
                fieldInfo.setSqlType(sqlType);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extraInfo));
                fieldInfo.setComment(comment);
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(processSqlTypeToJavaType(sqlType));
                //判断是否有date类型,方便后续使用
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, sqlType)) {
                    haveDateTime = true;
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, sqlType)) {
                    haveDate = true;
                }
                //判断是否有Decimal类型,方便后续使用
                if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPES, sqlType)) {
                    haveBigDecimal = true;
                }
                //构建String模糊搜索属性
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                    String fuzzyPropertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_FUZZY);

                    FieldInfo fuzzyField = new FieldInfo();
                    fuzzyField.setJavaType(fieldInfo.getJavaType());
                    fuzzyField.setPropertyName(fuzzyPropertyName);
                    fuzzyField.setFieldName(fieldInfo.getFieldName());
                    fuzzyField.setSqlType(sqlType);
                    fieldExtendList.add(fuzzyField);
                }
                //构建日期类模糊搜索属性
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    String startPropertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_START);
                    String endPropertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_END);

                    FieldInfo startField = new FieldInfo();
                    startField.setJavaType("String");
                    startField.setPropertyName(startPropertyName);
                    startField.setFieldName(fieldInfo.getFieldName());
                    startField.setSqlType(sqlType);
                    fieldExtendList.add(startField);
                    FieldInfo endField = new FieldInfo();
                    endField.setJavaType("String");
                    endField.setFieldName(fieldInfo.getFieldName());
                    endField.setPropertyName(endPropertyName);
                    endField.setSqlType(sqlType);
                    fieldExtendList.add(endField);
                }
                fieldInfoList.add(fieldInfo);
            }
            tableInfo.setHaveDate(haveDate);
            tableInfo.setHaveBigDecimal(haveBigDecimal);
            tableInfo.setHaveDateTime(haveDateTime);
            tableInfo.setFieldList(fieldInfoList);
            tableInfo.setFieldExtendList(fieldExtendList);
        } catch (Exception e) {
            logger.error("读取指定表信息失败", e);

        } finally {
            closeResultSet(fieldResult);
            closePreparedStatement(ps);
        }
    }

    /**
     * 读取每张表的索引信息
     *
     * @param tableInfo
     */
    public static void getKeyIndexInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet indexResult = null;
        try {
            Map<String, FieldInfo> tempMap = new HashMap<>();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                tempMap.put(fieldInfo.getFieldName(), fieldInfo);
            }

            //拿到指定表的所有索引集合
            ps = conn.prepareStatement(String.format(SQL_SHOW_INDEX_FROM, tableInfo.getTableName()));
            indexResult = ps.executeQuery();
            while (indexResult.next()) {
                String keyName = indexResult.getString("key_name");
                Integer nonUnique = indexResult.getInt("non_unique");
                String columnName = indexResult.getString("column_name");
                if (nonUnique == 1) {
                    continue;
                }
                //根据keyName去拿列表集合
                List<FieldInfo> indexInfoList = tableInfo.getKeyIndexMap().get(keyName);
                if (indexInfoList == null) {
                    indexInfoList = new ArrayList<FieldInfo>();
                    tableInfo.getKeyIndexMap().put(keyName, indexInfoList);
                }
                indexInfoList.add(tempMap.get(columnName));
            }
        } catch (Exception e) {
            logger.error("读取指定表索引信息失败", e);

        } finally {
            closeResultSet(indexResult);
            closePreparedStatement(ps);
        }
    }

    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String processFieldName(String field, Boolean upperCaseFirstLetter) {
        StringBuffer sb = new StringBuffer();
        String[] fields = field.split("_");
        sb.append(upperCaseFirstLetter ? StringUtils.upperCaseFirstLetter(fields[0]) : fields[0]);
        for (int i = 1; i < fields.length; i++) {
            sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
        }
        return sb.toString();
    }

    private static String processSqlTypeToJavaType(String sqlType) {
        if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPES, sqlType)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPES, sqlType)) {
            return "Long";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, sqlType)) {
            return "String";
        } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, sqlType) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, sqlType)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPES, sqlType)) {
            return "BigDecimal";
        } else {
            throw new RuntimeException("无法识别的数据类型" + sqlType);
        }
    }
}
