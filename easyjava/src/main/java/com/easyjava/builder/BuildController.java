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
public class BuildController {
    private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_CONTROLLER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + "Controller";
        String serviceName = tableInfo.getBeanName() + "Service";
        String serviceBeanName = StringUtils.lowerCaseFirstLetter(serviceName);
        File poFile = new File(folder, className + ".java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(poFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //先导包名
            bufferedWriter.write("package " + Constants.PACKAGE_CONTROLLER + ";");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            //import 需要的包名
            importNeededPackage(tableInfo, className, bufferedWriter, serviceName);
            //类的注释
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment() + "的Controller类");
            //注解
            buildAnnotation(className, bufferedWriter, StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()));
            //导入Service
            importService(serviceBeanName, bufferedWriter, serviceName);
            bufferedWriter.newLine();

            //构建方法
            //根据条件分页查询
            buildConditionalPageSearch(tableInfo, serviceBeanName, bufferedWriter);
            //新增
            insertOne(tableInfo, bufferedWriter, serviceBeanName);
            //批量新增
            insertBatch(tableInfo, bufferedWriter, serviceBeanName);
            //新增或修改
            insertOrUpdateOne(tableInfo, bufferedWriter, serviceBeanName);
            //批量新增或修改
            insertOrUpdateBatch(tableInfo, bufferedWriter, serviceBeanName);
            //通过索引的增删改
            crudByKey(tableInfo, bufferedWriter, serviceBeanName);
            bufferedWriter.newLine();
            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("创建Controller失败", e);
        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }

    private static void buildConditionalPageSearch(TableInfo tableInfo, String serviceBeanName, BufferedWriter bufferedWriter) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "根据条件分页查询");
        bufferedWriter.write("\t@RequestMapping(\"loadDataList\")");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query) {");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".findListByPage(query));");
        bufferedWriter.newLine();
        bufferedWriter.write("\t}");
        bufferedWriter.newLine();
    }

    private static void crudByKey(TableInfo tableInfo, BufferedWriter bufferedWriter, String serviceBeanName) throws Exception {
        Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
        for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
            StringBuilder methodName = new StringBuilder();
            StringBuilder methodParams = new StringBuilder();
            StringBuilder methodUse = new StringBuilder();

            List<FieldInfo> keyFieldInfoList = entry.getValue();
            int index = 0;
            for (FieldInfo fieldInfo : keyFieldInfoList) {
                index++;
                methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
                if (index < keyFieldInfoList.size()) {
                    methodName.append("And");
                }
                methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                methodUse.append(fieldInfo.getPropertyName());
                if (index < keyFieldInfoList.size()) {
                    methodParams.append(",");
                    methodUse.append(",");
                }
            }
            //构建查询方法
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "查询");
            bufferedWriter.write("\t@RequestMapping(\"" + "get" + tableInfo.getBeanName() + "By" + methodName + "\")");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public ResponseVO get" + tableInfo.getBeanName() + "By" + methodName.toString() + "(" + methodParams + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn getSuccessResponseVo(this." + serviceBeanName + ".getBy" + methodName + "(" + methodUse + "));");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();

            //构建更新方法
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "更新");
            bufferedWriter.write("\t@RequestMapping(\"" + "update" + tableInfo.getBeanName() + "By" + methodName + "\")");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public ResponseVO update" + tableInfo.getBeanName() + "By" + methodName + "(" + tableInfo.getBeanName() + " bean," + methodParams + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn getSuccessResponseVo(this." + serviceBeanName + ".updateBy" + methodName + "(bean," + methodUse + "));");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();

            //构建删除方法
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName + "删除");
            bufferedWriter.write("\t@RequestMapping(\"" + "delete" + tableInfo.getBeanName() + "By" + methodName + "\")");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public ResponseVO delete" + tableInfo.getBeanName() + "By" + methodName.toString() + "(" + methodParams + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn getSuccessResponseVo(this." + serviceBeanName + ".deleteBy" + methodName + "(" + methodUse + "));");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();
        }
    }

    private static void insertOrUpdateBatch(TableInfo tableInfo, BufferedWriter bufferedWriter, String serviceBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "批量新增或修改");
        bufferedWriter.write("\t@RequestMapping(\"addOrUpdateBatch\")");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic ResponseVO addOrUpdate(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".addOrUpdateBatch(listBean));");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }


    private static void insertOne(TableInfo tableInfo, BufferedWriter bufferedWriter, String serviceBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "新增");
        bufferedWriter.write("\t@RequestMapping(\"add\")");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".add(bean));");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void insertOrUpdateOne(TableInfo tableInfo, BufferedWriter bufferedWriter, String serviceBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "新增或者修改");
        bufferedWriter.write("\t@RequestMapping(\"addOrUpdate\")");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic ResponseVO addOrUpdate(" + tableInfo.getBeanName() + " bean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".addOrUpdate(bean));");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }


    private static void insertBatch(TableInfo tableInfo, BufferedWriter bufferedWriter, String serviceBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "批量新增");
        bufferedWriter.write("\t@RequestMapping(\"addBatch\")");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic ResponseVO addBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".addBatch(listBean));");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void importService(String serviceBeanName, BufferedWriter bufferedWriter, String serviceName) throws Exception {
        bufferedWriter.write("\t@Resource");
        bufferedWriter.newLine();
        bufferedWriter.write("\tprivate " + serviceName + " " + serviceBeanName + ";");
        bufferedWriter.newLine();
    }

    private static void buildAnnotation(String className, BufferedWriter bufferedWriter, String restMapping) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.write("@RestController");
        bufferedWriter.newLine();
        bufferedWriter.write("@RequestMapping(\"/" + restMapping + "\")");
        bufferedWriter.newLine();
        bufferedWriter.write("public class " + className + " extends ABaseController{");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
    }

    private static void importNeededPackage(TableInfo tableInfo, String interfaceName, BufferedWriter bufferedWriter, String serviceName) throws IOException {
        bufferedWriter.write("import org.springframework.web.bind.annotation.RestController;");
        bufferedWriter.newLine();
        bufferedWriter.write("import javax.annotation.Resource;");
        bufferedWriter.newLine();
        bufferedWriter.write("import org.springframework.web.bind.annotation.RequestMapping;");
        bufferedWriter.newLine();
        bufferedWriter.write("import org.springframework.web.bind.annotation.RequestBody;");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_SERVICE + "." + serviceName + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        bufferedWriter.newLine();
        bufferedWriter.write("import java.util.List;");
        bufferedWriter.newLine();
    }
}
