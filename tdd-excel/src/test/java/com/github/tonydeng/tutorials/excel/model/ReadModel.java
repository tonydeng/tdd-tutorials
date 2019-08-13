package com.github.tonydeng.tutorials.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReadModel extends BaseReadModel {

    @ExcelProperty(index = 2)
    private Integer mm;

    @ExcelProperty(index = 3)
    private BigDecimal money;

    @ExcelProperty(index = 4)
    private Long times;

    @ExcelProperty(index = 5)
    private Double activityCode;

    @ExcelProperty(index = 6, format = "yyyy-MM-dd")
    private Date date;

    @ExcelProperty(index = 7)
    private String lx;

    @ExcelProperty(index = 8)
    private String name;

    @ExcelProperty(index = 18)
    private String kk;

}
