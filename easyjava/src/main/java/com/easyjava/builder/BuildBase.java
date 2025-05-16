package com.easyjava.builder;

import com.easyjava.constant.Constants;
import com.easyjava.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gao98
 */
public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);


    public static void execute() {
        List<String> headerInfoList = new ArrayList<>();
        //生成日期枚举类
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
        build(headerInfoList, "DateTimePatternEnum", Constants.PATH_ENUMS);
        headerInfoList.clear();

        //生成时间转换工具类
        headerInfoList.add("package " + Constants.PACKAGE_UTILS);
        build(headerInfoList, "DateUtils", Constants.PATH_DATE_UTILS);
        //生成BaseMapper类
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_MAPPERS);
        build(headerInfoList, "BaseMapper", Constants.PATH_MAPPERS);

        //生成pageSize枚举
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
        build(headerInfoList, "PageSize", Constants.PATH_ENUMS);

        //生成responseCode枚举
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
        build(headerInfoList, "ResponseCodeEnum", Constants.PATH_ENUMS);

        //生成SimplePage类
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY);
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
        build(headerInfoList, "SimplePage", Constants.PATH_QUERY);

        //生成BaseQuery类
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_QUERY);
        build(headerInfoList, "BaseQuery", Constants.PATH_QUERY);

        //生成paginationResultVO
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_VO);
        build(headerInfoList, "PaginationResultVO", Constants.PATH_VO);

        //生成ResponseVO
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_VO);
        build(headerInfoList, "ResponseVO", Constants.PATH_VO);

        //生成BusinessException
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_EXCEPTION);
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        build(headerInfoList, "BusinessException", Constants.PATH_EXCEPTION);

        //生成BaseController
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER);
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        headerInfoList.add("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        build(headerInfoList, "ABaseController", Constants.PATH_CONTROLLER);

        //生成AGlobalExceptionHandlerController
        headerInfoList.clear();
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER);
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        headerInfoList.add("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        headerInfoList.add("import " + Constants.PACKAGE_EXCEPTION + ".BusinessException;");
        build(headerInfoList, "AGlobalExceptionHandlerController", Constants.PATH_CONTROLLER);

    }

    private static void build(List<String> headerInfoList, String fileName, String outputPath) {
        File folder = new File(outputPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File javaFile = new File(outputPath, fileName + ".java");

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            outputStream = new FileOutputStream(javaFile);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String inputFilePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").toURI().getPath();
            inputStream = new FileInputStream(inputFilePath);
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            for (String headinfo : headerInfoList) {
                bufferedWriter.write(headinfo + ";");
                bufferedWriter.newLine();
                if (headinfo.contains("package")) {
                    bufferedWriter.newLine();
                }
            }
            String lineInfo;
            while ((lineInfo = bufferedReader.readLine()) != null) {
                bufferedWriter.write(lineInfo);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            logger.error("生成基础类:{}失败,{}", fileName, e);
        } finally {
            StreamUtils.closeInputStream(inputStream, inputStreamReader, bufferedReader);
            StreamUtils.closeOutputStream(outputStream, outputStreamWriter, bufferedWriter);
        }
    }


}
