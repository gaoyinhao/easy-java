package com.easyjava.builder;

import com.easyjava.constant.Constants;
import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gao98
 */
public class BuildComment {

    /**
     * 构建类注释
     *
     * @param bufferedWriter
     * @param classComment
     * @throws Exception
     */
    public static void buildClassComment(BufferedWriter bufferedWriter, String classComment) throws Exception {
        bufferedWriter.write("/**");
        bufferedWriter.newLine();
        bufferedWriter.write(" * @author " + Constants.AUTHOR_COMMENT);
        bufferedWriter.newLine();
        bufferedWriter.write(" * @Description: " + classComment);
        bufferedWriter.newLine();
        bufferedWriter.write(" * @date: " + DateUtils.format(new Date(), DateUtils.datePattern2));
        bufferedWriter.newLine();
        bufferedWriter.write(" */");
        bufferedWriter.newLine();
    }

    /**
     * 构建属性注释
     *
     * @param bufferedWriter
     */
    public static void buildFieldComment(BufferedWriter bufferedWriter, String fieldComment) throws Exception {
        bufferedWriter.write("\t/**");
        bufferedWriter.newLine();
        bufferedWriter.write("\t * " + (fieldComment == null ? "" : fieldComment));
        bufferedWriter.newLine();
        bufferedWriter.write("\t */");
        bufferedWriter.newLine();
    }

    /**
     * 构建方法注释
     *
     * @param bufferedWriter
     */
    public static void buildMethodComment(BufferedWriter bufferedWriter, String methodComment) throws Exception {
        bufferedWriter.write("\t/**");
        bufferedWriter.newLine();
        bufferedWriter.write("\t * " + (methodComment == null ? "" : methodComment));
        bufferedWriter.newLine();
        bufferedWriter.write("\t */");
        bufferedWriter.newLine();
    }

}
