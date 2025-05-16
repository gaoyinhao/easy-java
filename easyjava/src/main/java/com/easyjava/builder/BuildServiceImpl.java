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
public class BuildServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE_IMPL);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String interfaceName = tableInfo.getBeanName() + "Service";
        File poFile = new File(folder, interfaceName + "Impl.java");
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(poFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //先导包名
            bufferedWriter.write("package " + Constants.PACKAGE_SERVICE_IMPL + ";");

            String mapperName = tableInfo.getBeanName() + StringUtils.upperCaseFirstLetter(Constants.SUFFIX_MAPPERS);
            String mapperBeanName = StringUtils.lowerCaseFirstLetter(mapperName);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            //导入需要的包
            importNeededPackage(tableInfo, interfaceName, bufferedWriter, mapperName);
            //类的注解
            BuildComment.buildClassComment(bufferedWriter, tableInfo.getComment() + "对应的ServiceImpl");
            //构造service注解和类名
            buildAnnotation(interfaceName, bufferedWriter);
            //导入Mapper
            importMapper(tableInfo, bufferedWriter, mapperName);

            //构造方法
            //根据条件查询列表
            selectByParam(tableInfo, bufferedWriter, mapperBeanName);
            //根据条件查询数量
            selectCountByParam(tableInfo, bufferedWriter, mapperBeanName);
            //分页查询
            selectByPage(tableInfo, bufferedWriter);
            //新增
            insertOne(tableInfo, bufferedWriter, mapperBeanName);
            //批量新增
            insertBatch(tableInfo, bufferedWriter, mapperBeanName);
            //新增或修改
            insertOrUpdateOne(tableInfo, bufferedWriter, mapperBeanName);
            //批量新增或修改
            insertOrUpdateBatch(tableInfo, bufferedWriter, mapperBeanName);
            //通过索引的增删改
            crudByKey(tableInfo, bufferedWriter, mapperBeanName);
            bufferedWriter.newLine();

            bufferedWriter.write("}");
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("创建serviceImpl失败", e);
        } finally {
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }

    private static void crudByKey(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
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
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName.toString() + "查询");
            bufferedWriter.write("\t@Override");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public " + tableInfo.getBeanName() + " getBy" + methodName.toString() + "(" + methodParams.toString() + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn this." + mapperBeanName + ".selectBy" + methodName.toString() + "(" + methodUse.toString() + ");");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();

            //构建更新方法
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName.toString() + "更新");
            bufferedWriter.write("\t@Override");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public Integer updateBy" + methodName.toString() + "(" + tableInfo.getBeanName() + " bean , " + methodParams.toString() + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn this." + mapperBeanName + ".updateBy" + methodName.toString() + "(bean," + methodUse.toString() + ");");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();

            //构建删除方法
            BuildComment.buildFieldComment(bufferedWriter, "根据" + methodName.toString() + "删除");
            bufferedWriter.write("\t@Override");
            bufferedWriter.newLine();
            bufferedWriter.write("\t public Integer deleteBy" + methodName.toString() + "(" + methodParams.toString() + "){");
            bufferedWriter.newLine();
            bufferedWriter.write("\t\treturn this." + mapperBeanName + ".deleteBy" + methodName.toString() + "(" + methodUse.toString() + ");");
            bufferedWriter.newLine();
            bufferedWriter.write("\t }");
            bufferedWriter.newLine();
        }
    }

    private static void insertOrUpdateBatch(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "批量新增或修改");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tif(listBean==null || listBean.isEmpty()){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\treturn 0;");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t}");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".insertOrUpdateBatch(listBean);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void insertOne(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "新增");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".insert(bean);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void insertOrUpdateOne(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "新增或者修改");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic Integer addOrUpdate(" + tableInfo.getBeanName() + " " + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + "){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".insertOrUpdate(" + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + ");");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void insertBatch(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "批量新增");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tif(listBean==null || listBean.isEmpty()){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\treturn 0;");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\t}");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".insertBatch(listBean);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void selectByPage(TableInfo tableInfo, BufferedWriter bufferedWriter) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "分页查询");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic PaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query ){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tInteger count = this.findCountByParam(query); ");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tInteger pageSize=query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tSimplePage page=new SimplePage(query.getPageNo(),count,pageSize);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tquery.setSimplePage(page);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tList<" + tableInfo.getBeanName() + "> list = this.findListByParam(query);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\tPaginationResultVO<" + tableInfo.getBeanName() + "> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn result;");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void selectCountByParam(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "根据条件查询数量");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic Integer findCountByParam(" + tableInfo.getBeanParamName() + " query){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".selectCount(query);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void selectByParam(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperBeanName) throws Exception {
        BuildComment.buildFieldComment(bufferedWriter, "根据条件查询列表");
        bufferedWriter.write("\t@Override");
        bufferedWriter.newLine();
        bufferedWriter.write("\tpublic List<" + tableInfo.getBeanName() + ">findListByParam(" + tableInfo.getBeanParamName() + " query){");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\treturn this." + mapperBeanName + ".selectList(query);");
        bufferedWriter.newLine();
        bufferedWriter.write("\t }");
        bufferedWriter.newLine();
    }

    private static void importMapper(TableInfo tableInfo, BufferedWriter bufferedWriter, String mapperName) throws Exception {
        bufferedWriter.write("\t@Resource");
        bufferedWriter.newLine();
        bufferedWriter.write("\tprivate " + mapperName + "<" + tableInfo.getBeanName() + "," + tableInfo.getBeanParamName() + "> " + StringUtils.lowerCaseFirstLetter(mapperName) + ";");
        bufferedWriter.newLine();
    }

    private static void buildAnnotation(String interfaceName, BufferedWriter bufferedWriter) throws Exception {
        bufferedWriter.newLine();
        bufferedWriter.write("@Service(\"" + StringUtils.lowerCaseFirstLetter(interfaceName) + "\")");
        bufferedWriter.newLine();
        bufferedWriter.write("public class " + interfaceName + "Impl implements " + interfaceName + "{");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

    }

    private static void importNeededPackage(TableInfo tableInfo, String interfaceName, BufferedWriter bufferedWriter, String mapperName) throws Exception {
        bufferedWriter.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_QUERY + ".SimplePage;");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_MAPPERS + "." + mapperName + ";");
        bufferedWriter.newLine();
        bufferedWriter.write("import org.springframework.stereotype.Service;");
        bufferedWriter.newLine();
        bufferedWriter.write("import java.util.List;");
        bufferedWriter.newLine();
        bufferedWriter.write("import javax.annotation.Resource;");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
        bufferedWriter.newLine();
        bufferedWriter.write("import " + Constants.PACKAGE_SERVICE + "." + tableInfo.getBeanName() + "Service;");
        bufferedWriter.newLine();
    }
}
