package com.github.tonydeng.tutorials.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class BaseWriteModel extends BaseRowModel {
    @ExcelProperty(value = {"表头1", "表头1", "表头31"}, index = 0)
    protected String p1;

    @ExcelProperty(value = {"表头1", "表头1", "表头32"}, index = 1)
    protected String p2;
}
