package com.qring.common.base.util;

import java.io.File;
import java.util.Objects;

/**
 * @Author Qring
 * @Description 文件操作工具
 * @Date 2023/3/2 22:04
 * @Version 1.0
 */
public class FileUtil {


    public static void deleteFileWithEnd(File file, String end) {
        if (file == null || !file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            if (file.getName().endsWith(end)) {
                boolean delete = file.delete();
            }
            return;
        }
        if (file.listFiles() == null) {
            return;
        }
        for (File child : Objects.requireNonNull(file.listFiles())) {
            deleteFileWithEnd(child, end);
        }
    }

    public static void main(String[] args) {
        deleteFileWithEnd(new File("D:\\BaiduNetdiskDownload\\SpringSecurity+OAuth2"), ".ev4");
    }
}
