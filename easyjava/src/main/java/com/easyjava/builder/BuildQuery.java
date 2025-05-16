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
import java.util.List;

/**
 * @author gao98
 */
public class BuildQuery {

    private static final Logger logg = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY);
        File poFile = new File(folder, className + ".java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(poFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //生成声明包
            bufferedWriter.write("package " + Constants.PACKAGE_QUERY + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //生成导入的包
            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bufferedWriter.write("import java.util.Date;");
                bufferedWriter.newLine();
            }
            if (tableInfo.getHaveBigDecimal()) {
                bufferedWriter.write("import java.math.BigDecimal;");
                bufferedWriter.newLine();
            }

            //生成类和实现的接口,还有类注释
            bufferedWriter.newLine();
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment() + "查询对象");
            //创建类
            bufferedWriter.write("public class " + className + " extends BaseQuery{");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            //生成属性以及属性上的注解和注释
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bufferedWriter, fieldInfo.getComment());
                bufferedWriter.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                //构建String模糊搜索属性
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                    String propertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_FUZZY);
                    bufferedWriter.write("\tprivate " + fieldInfo.getJavaType() + " " + propertyName + ";");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();


                }
                //构建日期类模糊搜索属性
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    String startPropertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_START);
                    String endPropertyName = fieldInfo.getPropertyName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_BEAN_QUERY_TIME_END);
                    bufferedWriter.write("\tprivate String " + startPropertyName + ";");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();

                    bufferedWriter.write("\tprivate String " + endPropertyName + ";");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                }
            }
            //加上了模糊搜索生成的属性,方便生成get,set方法
            buildGetAndSet(bufferedWriter, tableInfo.getFieldList());
            buildGetAndSet(bufferedWriter, tableInfo.getFieldExtendList());
            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {

        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }

    private static void buildGetAndSet(BufferedWriter bufferedWriter, List<FieldInfo> fieldInfoList) throws Exception {
        //生成属性的set,get方法
        for (FieldInfo fieldInfo : fieldInfoList) {
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
    }
}
