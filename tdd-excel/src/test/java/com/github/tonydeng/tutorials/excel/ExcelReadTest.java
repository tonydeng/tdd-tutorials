package com.github.tonydeng.tutorials.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.github.tonydeng.tutorials.excel.listen.ExcelListener;
import com.github.tonydeng.tutorials.excel.model.ReadModel;
import com.github.tonydeng.tutorials.excel.model.ReadModel2;
import com.github.tonydeng.tutorials.excel.utils.FileUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ExcelReadTest {


    /**
     * 07版本excel读数据量少于1千行数据，内部采用回调方法
     */
    @Test
    void testSimpleReadListStringV2007() {
        try (InputStream is = FileUtils.getResourcesFileInputStream("2007.xlsx")) {
            List<Object> data = EasyExcelFactory.read(is, new Sheet(1, 0));
            print(data);
        } catch (IOException e) {
            log.error("simpleReadListStringV2007 error", e);
        }
    }

    @Test
    void testSimpleReadJavaModelV2007() {
        try (InputStream is = FileUtils.getResourcesFileInputStream("2007.xlsx")) {
            List<Object> data = EasyExcelFactory.read(is, new Sheet(2, 1, ReadModel.class));
            print(data);
        } catch (IOException e) {
            log.error("simpleReadJavaModelV2007 error", e);
        }
    }

    @Test
    void testSaxReadListStringV2007() {
        try (InputStream is = FileUtils.getResourcesFileInputStream("2007.xlsx")) {
            ExcelListener listener = new ExcelListener();
            EasyExcelFactory.readBySax(is, new Sheet(1, 1), listener);
        } catch (IOException e) {
            log.error("testSaxReadListStringV2007 error", e);
        }
    }

    @Test
    void testSaxReadJavaModelV2007() {
        try (InputStream is = FileUtils.getResourcesFileInputStream("2007.xlsx")) {
            EasyExcelFactory.readBySax(is, new Sheet(2, 1, ReadModel.class), new ExcelListener());
        } catch (IOException e) {
            log.error("testSaxReadListStringV2007 error", e);
        }
    }

    @Test
    void testSaxReadSheetsV2007() {
        try (InputStream is = FileUtils.getResourcesFileInputStream("2007.xlsx")) {
            ExcelListener listener = new ExcelListener();
            ExcelReader reader = EasyExcelFactory.getReader(is, listener);

            List<Sheet> sheets = reader.getSheets();
            log.info("sheets {}", sheets);
            for (Sheet sheet : sheets) {
                switch (sheet.getSheetNo()){
                    case 1:
                        reader.read(sheet);
                    case 2:
                        sheet.setHeadLineMun(1);
                        sheet.setClazz(ReadModel.class);
                        reader.read(sheet);
                    case 3:
                        sheet.setHeadLineMun(1);
                        sheet.setClazz(ReadModel2.class);
                        reader.read(sheet);
                }
            }
        } catch (IOException e) {
            log.error("testSaxReadSheetsV2007 error", e);
        }
    }

    private void print(List<Object> datas) {
        datas.forEach(d -> log.info("{}", d));
    }
}
