package com.easyjava.builder;

import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.constant.Constants;
import com.easyjava.utils.StreamUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gao98
 */
public class BuildMapperXml {
    private static Logger logger = LoggerFactory.getLogger(BuildMapperXml.class);
    private static final String BASE_COLUMN_LIST = "base_column_list";
    private static final String BASE_QUERY_CONDITION = "base_query_condition";
    private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";
    private static final String QUERY_CONDITION = "query_condition";

    public static void execute(TableInfo tableInfo) {
        //先创建mapper.xml所在的文件夹
        File folder = new File(Constants.PATH_MAPPERS_XML);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //构建Mapper的类名
        String className = tableInfo.getBeanName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_MAPPERS);
        //PO的类名
        String poClass = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
        //主键的集合
        Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
        FieldInfo idField = null;
        for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("PRIMARY")) {
                List<FieldInfo> valueList = entry.getValue();
                if (valueList.size() == 1) {
                    idField = valueList.get(0);
                }
            }
        }
        //生成Mapper.xml文件
        File mapperFile = new File(folder, className + ".xml");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(mapperFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //开始生成Xml
            buildXmlHeader(bufferedWriter, className);
            //生成resultMap
            buildResultMap(tableInfo, poClass, idField, bufferedWriter);
            //生成通用查询列
            buildCommonSelectCol(tableInfo, bufferedWriter);
            //基础查询条件
            buildBaseSelectCondition(tableInfo, bufferedWriter);
            //扩展查询条件
            buildExtendSelectionCondition(tableInfo, bufferedWriter);
            //通用查询条件
            buildCommonSelectCondition(bufferedWriter);
            //查询列表
            buildSelectList(tableInfo, bufferedWriter);
            //查询数量
            buildSelectCount(tableInfo, bufferedWriter);
            //单条插入
            buildInsertOne(tableInfo, poClass, bufferedWriter);
            //插入或更新
            buildInsertOrUpdate(tableInfo, poClass, keyIndexMap, bufferedWriter);
            //添加(批量插入)
            buildInsertBatch(tableInfo, poClass, bufferedWriter);
            //批量插入或者更新
            buildInsertOrUpdateBatch(tableInfo, poClass, bufferedWriter);
            //根据索引进行增删改
            buildCrudByIndex(tableInfo, poClass, keyIndexMap, bufferedWriter);

            bufferedWriter.newLine();
            bufferedWriter.write("</mapper>");
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("创建MapperXml失败", e);
        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }

    private static void buildCrudByIndex(TableInfo tableInfo, String poClass, Map<String, List<FieldInfo>> keyIndexMap, BufferedWriter bufferedWriter) throws Exception {
        for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
            StringBuilder methodName = new StringBuilder();
            StringBuilder paramNames = new StringBuilder();
            List<FieldInfo> keyFieldInfoList = entry.getValue();
            int index = 0;
            for (FieldInfo fieldInfo : keyFieldInfoList) {
                index++;
                methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
                paramNames.append(fieldInfo.getFieldName() + "=#{" + fieldInfo.getPropertyName() + "}");
                if (index < keyFieldInfoList.size()) {
                    methodName.append("And");
                    paramNames.append(" and ");
                }
            }
            bufferedWriter.newLine();
            //构造查询方法
            bufferedWriter.write("\t<!-- 根据\"" + methodName + "\"查询-->");
            bufferedWriter.newLine();
            bufferedWriter.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\tselect <include refid=\"" + BASE_COLUMN_LIST + "\"/>  from " + tableInfo.getTableName() +
                    " where " + paramNames);
            bufferedWriter.newLine();
            bufferedWriter.write("\t</select>");
            //构造更新方法
            bufferedWriter.newLine();
            bufferedWriter.write("\t<!-- 根据\"" + methodName + "\"更新-->");
            bufferedWriter.newLine();
            bufferedWriter.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\"" + poClass + "\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\tupdate  " + tableInfo.getTableName());
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t<set>");
            bufferedWriter.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bufferedWriter.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\t\t" + fieldInfo.getFieldName() + "=#{bean." + fieldInfo.getPropertyName() + "},");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\t\t</if>");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("\t\t</set>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t<where>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t" + paramNames);
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t</where>");
            bufferedWriter.newLine();
            bufferedWriter.write("\t</update>");
            bufferedWriter.newLine();
            //构造删除方法
            bufferedWriter.newLine();
            bufferedWriter.write("\t<!-- 根据\"" + methodName + "\"删除-->");
            bufferedWriter.newLine();
            bufferedWriter.write("\t<delete id=\"deleteBy" + methodName + "\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\tdelete from " + tableInfo.getTableName() + " where " + paramNames);
            bufferedWriter.newLine();
            bufferedWriter.write("\t</delete>");
            bufferedWriter.newLine();
        }
    }

    private static void buildInsertOrUpdateBatch(TableInfo tableInfo, String poClass, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.newLine();
        bufferedWriter.write("\t<!--批量插入或更新-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + poClass + "\">");
        bufferedWriter.newLine();
        insertBatchSamePath(tableInfo, bufferedWriter);
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\ton DUPLICATE key update");
        bufferedWriter.newLine();
        StringBuilder insertBatchUpdateBuilder = new StringBuilder();
        insertBatchUpdateBuilder.append("            ");
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            insertBatchUpdateBuilder.append(fieldInfo.getFieldName() + "= VALUES(" + fieldInfo.getFieldName() + "),");
        }
        String batchInsertOrUpdate = insertBatchUpdateBuilder.substring(0, insertBatchUpdateBuilder.lastIndexOf(","));
        bufferedWriter.write(batchInsertOrUpdate);
        bufferedWriter.newLine();
        bufferedWriter.write("\t</insert>");
    }

    private static void buildInsertBatch(TableInfo tableInfo, String poClass, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("\t<!--添加(批量插入)-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<insert id=\"insertBatch\" parameterType=\"" + poClass + "\">");
        bufferedWriter.newLine();
        insertBatchSamePath(tableInfo, bufferedWriter);
        bufferedWriter.newLine();
        bufferedWriter.write("\t </insert>");
    }

    private static void insertBatchSamePath(TableInfo tableInfo, BufferedWriter bufferedWriter) throws IOException {
        StringBuilder insertFileStringBuilder = new StringBuilder();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            if (fieldInfo.getAutoIncrement()) {
                continue;
            }
            insertFileStringBuilder.append(fieldInfo.getFieldName()).append(",");
        }
        String string = insertFileStringBuilder.substring(0, insertFileStringBuilder.lastIndexOf(","));
        bufferedWriter.write("\t\tinsert into " + tableInfo.getTableName() + "(" + string + ")values");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
        bufferedWriter.newLine();
        StringBuilder insertPropertyBuilder = new StringBuilder();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            if (fieldInfo.getAutoIncrement()) {
                continue;
            }
            insertPropertyBuilder.append("#{item." + fieldInfo.getPropertyName() + "}").append(",");
        }
        String substring = insertPropertyBuilder.substring(0, insertPropertyBuilder.lastIndexOf(","));
        bufferedWriter.write("\t\t\t("+substring+")");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t</foreach>");
    }


    private static void buildInsertSamePart(BufferedWriter bufferedWriter, TableInfo tableInfo) throws Exception {
        bufferedWriter.write("\t\tinsert into " + tableInfo.getTableName());
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            bufferedWriter.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t</if>");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("\t\t</trim>");
        bufferedWriter.newLine();
        //生成values()
        bufferedWriter.write("\t\t<trim prefix=\"values(\" suffix=\")\" suffixOverrides=\",\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            bufferedWriter.write(" \t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
            bufferedWriter.newLine();
            bufferedWriter.write(" \t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t </if>");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("\t\t </trim>");
        bufferedWriter.newLine();
    }

    private static void buildInsertOrUpdate(TableInfo tableInfo, String poClass, Map<String, List<FieldInfo>> keyIndexMap, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--插入或者更新(匹配有值的字段)-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<insert id=\"insertOrUpdate\"  parameterType=\"" + poClass + "\">");
        bufferedWriter.newLine();
        buildInsertSamePart(bufferedWriter, tableInfo);
        //找出主键
        Set<String> tempSet = new HashSet<>();
        for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
            List<FieldInfo> fieldInfoList = entry.getValue();
            for (FieldInfo item : fieldInfoList) {
                tempSet.add(item.getFieldName());
            }
        }
        bufferedWriter.write("\t\ton DUPLICATE key update");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            if (tempSet.contains(fieldInfo.getFieldName())) {
                continue;
            }
            bufferedWriter.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t\t" + fieldInfo.getFieldName() + " =VALUES(" + fieldInfo.getFieldName() + "),");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t</if>");
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t</trim>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t</insert>");
        bufferedWriter.newLine();
    }


    private static void buildInsertOne(TableInfo tableInfo, String poClass, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--插入(匹配有值的字段)-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<insert id=\"insert\"  parameterType=\"" + poClass + "\">");
        bufferedWriter.newLine();
        FieldInfo autoIncrementField = null;
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()) {
                autoIncrementField = fieldInfo;
                break;
            }
        }
        if (autoIncrementField != null) {
            bufferedWriter.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementField.getFieldName() + "\" resultType=\"" + autoIncrementField.getJavaType() + "\" order=\"AFTER\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\tSELECT LAST_INSERT_ID();");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t</selectKey>");
            bufferedWriter.newLine();
        }
        buildInsertSamePart(bufferedWriter, tableInfo);
        bufferedWriter.write("\t</insert>");
        bufferedWriter.newLine();

    }

    private static void buildSelectCount(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("  <!--查询数量-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<select id=\"selectCount\"  resultType=\"java.lang.Integer\">");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tSELECT count(1) FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t</select>");
        bufferedWriter.newLine();
    }

    private static void buildSelectList(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--查询列表-->");
        bufferedWriter.newLine();
        bufferedWriter.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tSELECT <include refid=\"" + BASE_COLUMN_LIST + "\"/> FROM " + tableInfo.getTableName() + " <include refid=\"" +
                QUERY_CONDITION + "\"/>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<if test=\"query.orderBy!=null\"> order by ${query.orderBy}</if>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<if test=\"query.simplePage!=null\">limit #{query.simplePage.start},#{query.simplePage.end}</if>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t</select>");
        bufferedWriter.newLine();
    }

    private static void buildCommonSelectCondition(BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--通用查询条件-->");
        bufferedWriter.newLine();
        bufferedWriter.write("    <sql id=\"" + QUERY_CONDITION + "\">");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t<where>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION + "\"/>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_EXTEND + "\"/>");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t</where>");
        bufferedWriter.newLine();
        bufferedWriter.write("    </sql>");
        bufferedWriter.newLine();
    }

    private static void buildExtendSelectionCondition(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--扩展查询条件-->");
        bufferedWriter.newLine();
        bufferedWriter.write("    <sql id=\"" + BASE_QUERY_CONDITION_EXTEND + "\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
            String andWhere = "";
            if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                andWhere = "and " + fieldInfo.getFieldName() + " like concat('%',#{query." + fieldInfo.getPropertyName() + "},'%')";
            } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                if (fieldInfo.getPropertyName().endsWith(StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_START))) {
                    andWhere = "<![CDATA[ and  " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "},'%Y-%m-%d') ]]>";
                } else if (fieldInfo.getPropertyName().endsWith(StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_END))) {
                    andWhere = "<![CDATA[ and  " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + fieldInfo.getPropertyName() + "},'%Y-%m-%d')," +
                            "interval -1 day) ]]>";
                }
            }
            bufferedWriter.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + "!=null" + " and query." + fieldInfo.getPropertyName() + "!=''" + "\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\t" + andWhere);
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t</if>");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("    </sql>");
        bufferedWriter.newLine();

    }

    private static void buildBaseSelectCondition(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--基础查询条件-->");
        bufferedWriter.newLine();
        bufferedWriter.write("    <sql id=\"" + BASE_QUERY_CONDITION + "\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            String stringQuery = "";
            if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                stringQuery = " and query." + fieldInfo.getPropertyName() + "!=''";
            }
            bufferedWriter.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + "!=null" + stringQuery + "\">");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t\tand " + fieldInfo.getFieldName() + "=#{query." + fieldInfo.getPropertyName() + "} ");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\t</if>");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("    </sql>");
        bufferedWriter.newLine();
    }

    private static void buildCommonSelectCol(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--通用查询结果列-->");
        bufferedWriter.newLine();
        bufferedWriter.write("<sql id=\"" + BASE_COLUMN_LIST + "\">");
        bufferedWriter.newLine();
        StringBuilder columnBuilder = new StringBuilder();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            columnBuilder.append(fieldInfo.getFieldName()).append(",");
        }
        String columnBuilderStr = columnBuilder.substring(0, columnBuilder.lastIndexOf(","));
        bufferedWriter.write("\t" + columnBuilderStr);
        bufferedWriter.newLine();
        bufferedWriter.write("    </sql>");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
    }

    private static void buildResultMap(TableInfo tableInfo, String poClass, FieldInfo idField, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.write("    <!--实体映射-->");
        bufferedWriter.newLine();
        bufferedWriter.write("    <resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
        bufferedWriter.newLine();
        for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
            bufferedWriter.write("        <!--" + fieldInfo.getComment() + "-->");
            bufferedWriter.newLine();
            String key = "";
            if (idField != null && idField.getPropertyName().equals(fieldInfo.getPropertyName())) {
                key = "id";
            } else {
                key = "result";
            }
            bufferedWriter.write("        <" + key + " column=\"" + fieldInfo.getFieldName() + "\" property=\"" + fieldInfo.getPropertyName() + "\"/>");
            bufferedWriter.newLine();
        }
        bufferedWriter.write("   </resultMap>");
        bufferedWriter.newLine();
    }

    /**
     * 生成xml固定头部
     *
     * @param bufferedWriter
     * @param className
     */
    private static void buildXmlHeader(BufferedWriter bufferedWriter, String className) throws Exception {
        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        bufferedWriter.newLine();
        bufferedWriter.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bufferedWriter.newLine();
        bufferedWriter.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPERS + "." + className + "\">");
        bufferedWriter.newLine();

    }
}
