package com.github.tonydeng.tutorials.excel.utils;

import java.io.InputStream;

public class FileUtils {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("" + fileName);
    }

}
