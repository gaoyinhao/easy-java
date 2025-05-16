package com.easyjava.utils;

import java.io.*;

/**
 * @author gao98
 */
public class StreamUtils {

    public static void closeInputStream(InputStream inputStream, InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeOutputStream(OutputStream outputStream, OutputStreamWriter outputStreamWriter, BufferedWriter bufferedWriter) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStreamWriter != null) {
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
