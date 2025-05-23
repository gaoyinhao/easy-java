package com.easyjava.bean;

import java.io.Serializable;

/**
 * @author gao98
 */
public class FieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * mysql中的字段名称
     */
    private String FieldName;
    /**
     * java中的bean属性名称
     */
    private String propertyName;
    /**
     * mysql中属性的字段类型
     */
    private String sqlType;
    /**
     * java中属性的字段类型
     */
    private String javaType;
    /**
     * 字段备注
     */
    private String comment;
    /**
     * 字段是否自增长
     */
    private Boolean isAutoIncrement;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }


}
