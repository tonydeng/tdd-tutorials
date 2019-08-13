package com.github.tonydeng.tutorials.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class BaseReadModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    protected String str;

    @ExcelProperty(index = 1)
    protected Float ff;

}
