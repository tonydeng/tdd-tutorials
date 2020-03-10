package com.github.tonydeng.tutorials.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.github.tonydeng.tutorials.excel.model.WriteModel;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelWriteTest {

    @Test
    void writeV2007() {
        String f = System.getProperty("java.io.tmpdir") + File.separator + "2007.xlsx";

        try (OutputStream out = new FileOutputStream(f)) {
            ExcelWriter writer = EasyExcelFactory.getWriter(out);
            Sheet sheet1 = new Sheet(1, 3);
            sheet1.setSheetName("第一个sheet");

            Map<Integer, Integer> columnWidth = Maps.newHashMap();
            columnWidth.put(0, 10000);
            columnWidth.put(1, 40000);
            columnWidth.put(2, 10000);
            columnWidth.put(3, 10000);


            sheet1.setColumnWidthMap(columnWidth);
            sheet1.setHead(createTestListStringHead());
            sheet1.setAutoWidth(Boolean.TRUE);
            writer.write1(createTestListObject(), sheet1);


            //写第二个sheet sheet2  模型上打有表头的注解，合并单元格
            Sheet sheet2 = new Sheet(2, 3, WriteModel.class, "第二个sheet", null);
            sheet2.setTableStyle(createTableStyle());
            //writer.write1(null, sheet2);
            writer.write(createTestListJavaMode(), sheet2);
            //需要合并单元格
            writer.merge(5, 20, 1, 1);

            //写第三个sheet包含多个table情况
            Sheet sheet3 = new Sheet(3, 0);
            sheet3.setSheetName("第三个sheet");
            Table table1 = new Table(1);
            table1.setHead(createTestListStringHead());
            writer.write1(createTestListObject(), sheet3, table1);

            //写sheet2  模型上打有表头的注解
            Table table2 = new Table(2);
            table2.setTableStyle(createTableStyle());
            table2.setClazz(WriteModel.class);
            writer.write(createTestListJavaMode(), sheet3, table2);

            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<List<String>> createTestListStringHead() {
        //写sheet3  模型上没有注解，表头数据动态传入
        List<List<String>> head = Lists.newArrayList();
        List<String> headCoulumn1 = Lists.newArrayList();
        List<String> headCoulumn2 = Lists.newArrayList();
        List<String> headCoulumn3 = Lists.newArrayList();
        List<String> headCoulumn4 = Lists.newArrayList();
        List<String> headCoulumn5 = Lists.newArrayList();

        headCoulumn1.add("第一列");
        headCoulumn1.add("第一列");
        headCoulumn1.add("第一列");
        headCoulumn2.add("第一列");
        headCoulumn2.add("第一列");
        headCoulumn2.add("第一列");

        headCoulumn3.add("第二列");
        headCoulumn3.add("第二列");
        headCoulumn3.add("第二列");
        headCoulumn4.add("第三列");
        headCoulumn4.add("第三列2");
        headCoulumn4.add("第三列2");
        headCoulumn5.add("第一列");
        headCoulumn5.add("第3列");
        headCoulumn5.add("第4列");

        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        return head;
    }

    private static List<List<Object>> createTestListObject() {
        List<List<Object>> object = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            List<Object> da = new ArrayList<Object>();
            da.add("字符串" + i);
            da.add(Long.valueOf(187837834l + i));
            da.add(Integer.valueOf(2233 + i));
            da.add(Double.valueOf(2233.00 + i));
            da.add(Float.valueOf(2233.0f + i));
            da.add(new Date());
            da.add(new BigDecimal("3434343433554545" + i));
            da.add(Short.valueOf((short) i));
            object.add(da);
        }
        return object;
    }

    private static List<WriteModel> createTestListJavaMode() {
        List<WriteModel> model1s = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            WriteModel model1 = new WriteModel();
            model1.setP1("第一列，第行");
            model1.setP2("121212jjj");
            model1.setP3(33 + i);
            model1.setP4(44);
            model1.setP5("555");
            model1.setP6(666.2f);
            model1.setP7(new BigDecimal("454545656343434" + i));
            model1.setP8(new Date());
            model1.setP9("llll9999>&&&&&6666^^^^");
            model1.setP10(1111.77 + i);
            model1s.add(model1);
        }
        return model1s;
    }

    private static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 22);
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short) 22);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.GREEN);
        return tableStyle;
    }
}
