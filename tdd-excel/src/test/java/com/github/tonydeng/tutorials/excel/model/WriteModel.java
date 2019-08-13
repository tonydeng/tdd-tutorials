package com.github.tonydeng.tutorials.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WriteModel extends BaseWriteModel {

    @ExcelProperty(value = {"表头3","表头3","表头3"},index = 2)
    private int p3;

    @ExcelProperty(value = {"表头1","表头4","表头4"},index = 3)
    private long p4;

    @ExcelProperty(value = {"表头5","表头51","表头52"},index = 4)
    private String p5;

    @ExcelProperty(value = {"表头6","表头61","表头611"},index = 5)
    private float p6;

    @ExcelProperty(value = {"表头6","表头61","表头612"},index = 6)
    private BigDecimal p7;

    @ExcelProperty(value = {"表头6","表头62","表头621"},index = 7)
    private Date p8;

    @ExcelProperty(value = {"表头6","表头62","表头622"},index = 8)
    private String p9;

    @ExcelProperty(value = {"表头6","表头62","表头622"},index = 9)
    private double p10;

}
