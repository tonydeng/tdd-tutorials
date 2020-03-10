package com.github.tonydeng.tutorials.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class ExcelListener extends AnalysisEventListener {

    private List<Object> dataList = Lists.newArrayList();


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        if(!checkObjAllFieldsIsNull(checkObjAllFieldsIsNull(o))){
            dataList.add(o);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Object> getDataList() {
        return dataList;
    }


    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 判断对象中属性是否全为空
     *
     * @param object
     * @return
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        if (Objects.isNull(object)) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                ExcelProperty property = f.getAnnotation(ExcelProperty.class);

                if (property == null || SERIAL_VERSION_UID.equals(f.getName())) {
                    continue;
                }
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return true;
    }
}
