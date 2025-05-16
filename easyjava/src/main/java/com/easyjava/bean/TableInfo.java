package com.easyjava.bean;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gao98
 */
public class TableInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 参数名称
     */
    private String beanParamName;

    /**
     * 注释
     */
    private String comment;

    /**
     * 字段信息
     */
    private List<FieldInfo> fieldList;

    /**
     * 扩展字段信息
     */
    private List<FieldInfo> fieldExtendList;
    /**
     * 唯一索引集合
     */
    private Map<String, List<FieldInfo>> keyIndexMap = new LinkedHashMap<>();

    /**
     * 是否有date类型
     */
    private Boolean haveDate;

    /**
     * 是否有时间类型
     */
    private Boolean haveDateTime;

    /**
     * 是否有bigDecimal类型
     */
    private Boolean haveBigDecimal;

    /**
     * 是否有自增长id
     */
    private Boolean haveAutoIncrement;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanParamName() {
        return beanParamName;
    }

    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FieldInfo> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldInfo> fieldList) {
        this.fieldList = fieldList;
    }

    public List<FieldInfo> getFieldExtendList() {
        return fieldExtendList;
    }

    public void setFieldExtendList(List<FieldInfo> fieldExtendList) {
        this.fieldExtendList = fieldExtendList;
    }

    public Map<String, List<FieldInfo>> getKeyIndexMap() {
        return keyIndexMap;
    }

    public void setKeyIndexMap(Map<String, List<FieldInfo>> keyIndexMap) {
        this.keyIndexMap = keyIndexMap;
    }

    public Boolean getHaveDate() {
        return haveDate;
    }

    public void setHaveDate(Boolean haveDate) {
        this.haveDate = haveDate;
    }

    public Boolean getHaveDateTime() {
        return haveDateTime;
    }

    public void setHaveDateTime(Boolean haveDateTime) {
        this.haveDateTime = haveDateTime;
    }

    public Boolean getHaveBigDecimal() {
        return haveBigDecimal;
    }

    public void setHaveBigDecimal(Boolean haveBigDecimal) {
        this.haveBigDecimal = haveBigDecimal;
    }

    public Boolean getHaveAutoIncrement() {
        return haveAutoIncrement;
    }

    public void setHaveAutoIncrement(Boolean haveAutoIncrement) {
        this.haveAutoIncrement = haveAutoIncrement;
    }
}
