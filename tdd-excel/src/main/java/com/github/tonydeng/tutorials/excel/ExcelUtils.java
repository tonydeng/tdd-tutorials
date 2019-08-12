package com.github.tonydeng.tutorials.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class ExcelUtils {

    private ExcelUtils() {
    }

    public static <T extends BaseRowModel> List<T> readExcel(File excel, Class<T> rowModel,
            int sheetNo)
            throws ExcelException {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个sheet的excel
     */
    public static <T extends BaseRowModel> List<T> readExcel(File excel, Class<T> rowModel,
            int sheetNo, int headLineNum)
            throws ExcelException {
        ExcelListener listener = new ExcelListener();
        ExcelReader reader = getReader(excel, listener);
        if (Objects.isNull(reader)) {
            return Lists.newArrayList();
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel));

        return getExtendBeanList(listener.getDataList(), rowModel);
    }

    private static ExcelReader getReader(File excel, ExcelListener listener) throws ExcelException {
        String fileName = excel.getName();
        if (StringUtils.isBlank(fileName)) {
            throw new ExcelException("文件格式错误!");
        }

        if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) &&
                !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {

            throw new ExcelException("文件格式错误!");
        }

        try (InputStream is = new FileInputStream(excel)) {
            return new ExcelReader(is, null, listener, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends BaseRowModel> List<T> getExtendBeanList(List<?> list,
            Class<T> typeClazz) {
        return MyBeanCopy.convert(list, typeClazz);
    }
}
