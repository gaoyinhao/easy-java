package com.easyjava.builder;

import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.constant.Constants;
import com.easyjava.utils.DateUtils;
import com.easyjava.utils.StreamUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author gao98
 */
public class BuildPo {
    private static final Logger logg = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File poFile = new File(folder, tableInfo.getBeanName() + ".java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(poFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //生成声明包
            bufferedWriter.write("package " + Constants.PACKAGE_PO + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //生成导入的包
            bufferedWriter.write("import java.io.Serializable;");
            bufferedWriter.newLine();
            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bufferedWriter.write("import java.util.Date;");
                bufferedWriter.newLine();
                bufferedWriter.write(Constants.BEAN_DATE_JSON_FORMAT_CLASS + ";");
                bufferedWriter.newLine();
                bufferedWriter.write(Constants.BEAN_DATE_JSON_UN_FORMAT_CLASS + ";");
                bufferedWriter.newLine();
                bufferedWriter.write("import " + Constants.PACKAGE_ENUMS + ".DateTimePatternEnum;");
                bufferedWriter.newLine();
                bufferedWriter.write("import " + Constants.PACKAGE_UTILS + ".DateUtils;");
                bufferedWriter.newLine();
            }
            if (tableInfo.getHaveBigDecimal()) {
                bufferedWriter.write("import java.math.BigDecimal;");
                bufferedWriter.newLine();
            }
            //生成注解
            Boolean hasIgnoreBean = false;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TO_JSON_FIELD.split(","), fieldInfo.getPropertyName())) {
                    hasIgnoreBean = true;
                    break;
                }
            }
            if (hasIgnoreBean) {
                bufferedWriter.write(Constants.IGNORE_BEAN_TO_JSON_CLASS + ";");
                bufferedWriter.newLine();
            }
            //生成类和实现的接口还有注解
            bufferedWriter.newLine();
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment());
            bufferedWriter.write("public class " + tableInfo.getBeanName() + " implements Serializable{");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //生成属性和属性上的注解和注释
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bufferedWriter, fieldInfo.getComment());
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    bufferedWriter.write("\t" + String.format(Constants.BEAN_DATE_JSON_FORMAT_EXPRESSION, DateUtils.datePattern4));
                    bufferedWriter.newLine();
                    bufferedWriter.write("\t" + String.format(Constants.BEAN_DATE_JSON_UN_FORMAT_EXPRESSION, DateUtils.datePattern4));
                    bufferedWriter.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    bufferedWriter.write("\t" + String.format(Constants.BEAN_DATE_JSON_FORMAT_EXPRESSION, DateUtils.datePattern1));
                    bufferedWriter.newLine();
                    bufferedWriter.write("\t" + String.format(Constants.BEAN_DATE_JSON_UN_FORMAT_EXPRESSION, DateUtils.datePattern1));
                    bufferedWriter.newLine();
                }
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TO_JSON_FIELD.split(","), fieldInfo.getPropertyName())) {
                    bufferedWriter.write("\t" + Constants.IGNORE_BEAN_TO_JSON_EXPRESSION);
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            //生成set,get方法
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String tempField = StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName());
                bufferedWriter.write("\tpublic void set" + tempField + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ") {");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\tthis." + fieldInfo.getPropertyName() + " = " + fieldInfo.getPropertyName() + ";");
                bufferedWriter.newLine();
                bufferedWriter.write("\t}");
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.write("\tpublic " + fieldInfo.getJavaType() + " get" + tempField + "() {");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\treturn this." + fieldInfo.getPropertyName() + ";");
                bufferedWriter.newLine();
                bufferedWriter.write("\t}");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            //生成toString方法
            StringBuffer stringBuffer = new StringBuffer();
            Integer index = 0;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                index++;
                String tempPropertyName = fieldInfo.getPropertyName();
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    tempPropertyName = "DateUtils.format(" + tempPropertyName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    tempPropertyName = "DateUtils.format(" + tempPropertyName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }
                stringBuffer.append(fieldInfo.getComment() + ":\" + (" + fieldInfo.getPropertyName() + " == null ? \"空\" : " + tempPropertyName + ")");
                if (index < tableInfo.getFieldList().size()) {
                    stringBuffer.append(" + ").append("\",");
                }
            }
            String toStringStr = "\"" + stringBuffer.toString();
            bufferedWriter.write("\t@Override");
            bufferedWriter.newLine();
            bufferedWriter.write("\tpublic String toString() {");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn " + toStringStr + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("\t}");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {

        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }


}
