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
 * @author gao98
 */
public class BuildService {
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + "Service";
        File poFile = new File(folder, className + ".java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(poFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //先导包名
            bufferedWriter.write("package " + Constants.PACKAGE_SERVICE + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //导入需要的包
            bufferedWriter.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
            bufferedWriter.newLine();
            bufferedWriter.write("import java.util.List;");
            bufferedWriter.newLine();
            //类的注释
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment() + "对应的Service");
            bufferedWriter.newLine();
            //创建类
            bufferedWriter.write("public interface " + className + "{");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            BuildComment.buildFieldComment(bufferedWriter, "根据条件查询列表");
            bufferedWriter.write("\tList<" + tableInfo.getBeanName() + ">findListByParam(" + tableInfo.getBeanParamName() + " query);");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "根据条件查询数量");
            bufferedWriter.write("    Integer findCountByParam(" + tableInfo.getBeanParamName() + " query);");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "分页查询");
            bufferedWriter.write("\tPaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query );");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "新增");
            bufferedWriter.write("\tInteger add(" + tableInfo.getBeanName() + " bean);");
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "批量新增");
            bufferedWriter.write("\tInteger addBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "新增或修改");
            bufferedWriter.write("\tInteger addOrUpdate(" + tableInfo.getBeanName() + " bean);");
            bufferedWriter.newLine();

            BuildComment.buildFieldComment(bufferedWriter, "批量新增或修改");
            bufferedWriter.write("\tInteger addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bufferedWriter.newLine();

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
                    methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodParams.append(",");
                    }
                }
                //构建查询方法
                BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "查询");
                bufferedWriter.write("\t " + tableInfo.getBeanName() + " getBy" + methodName.toString() + "(" + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                //构建更新方法
                BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "查询");
                bufferedWriter.write("\t Integer updateBy" + methodName.toString() + "(" + tableInfo.getBeanName() + " bean , " + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                //构建删除方法
                BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "删除");
                bufferedWriter.write("\t Integer deleteBy" + methodName.toString() + "(" + methodParams + ");");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();

            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("创建service失败", e);
        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }
}
