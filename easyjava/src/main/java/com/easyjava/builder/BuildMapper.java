package com.easyjava.builder;

import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.constant.Constants;
import com.easyjava.utils.StreamUtils;
import com.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 构造Mapper
 *
 * @author gao98
 */
public class BuildMapper {

    private static Logger logger = LoggerFactory.getLogger(BuildMapper.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPERS);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_MAPPERS);
        File outputFile = new File(folder, className + ".java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(outputFile);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //先生成包名
            bufferedWriter.write("package " + Constants.PACKAGE_MAPPERS + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //生成需要导入的类
            bufferedWriter.write("import org.apache.ibatis.annotations.Param;");
            bufferedWriter.newLine();
            bufferedWriter.write("import org.apache.ibatis.annotations.Mapper;");
            bufferedWriter.newLine();
            //生成类注释
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment() + "的Mapper类");
            bufferedWriter.newLine();
            //生成类注解
            bufferedWriter.write("@Mapper");
            bufferedWriter.newLine();
            bufferedWriter.write("public interface " + className + "<T,P> extends BaseMapper {");
            bufferedWriter.newLine();

            //获得表的所有索引信息
            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();

                List<FieldInfo> keyFieldInfoList = entry.getValue();
                int index = 0;
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                    }
                    methodParams.append("@Param(\"" + fieldInfo.getPropertyName() + "\") " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodParams.append(",");
                    }
                }
                //生成查询方法和其注解
                BuildComment.buildMethodComment(bufferedWriter, "根据" + methodName + "查询");
                bufferedWriter.write("\t T selectBy" + methodName.toString() + "(" + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                //生成更新方法和注释
                BuildComment.buildMethodComment(bufferedWriter, "根据" + methodName + "更新");
                bufferedWriter.write("\t Integer updateBy" + methodName + "(@Param(\"bean\") T t, " + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                //构建删除方法
                BuildComment.buildMethodComment(bufferedWriter, "根据" + methodName + "删除");
                bufferedWriter.write("\t Integer deleteBy" + methodName.toString() + "(" + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("创建Mapper失败", e);
        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }

}
