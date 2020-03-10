package com.github.tonydeng.tutorials.excel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.util.Assert;

public class MyBeanCopy {


    public static <T> List<T> convert(List<?> sources, Class<T> targetClazz) {
        List<?> sourcesObj = sources;
        if (null == sourcesObj) {
            sourcesObj = Collections.emptyList();
        }
        List<T> targets = new ArrayList<>(sourcesObj.size());
        MyBeanCopy.convert(sourcesObj, targets, targetClazz);
        return targets;
    }

    private static <T> void convert(List<?> sources, List<T> targets, Class<T> targetClazz) {
        if (targets == null) {
            return;
        }
        targets.clear();
        if (sources == null) {
            return;
        }

        for (Object obj : sources) {
            try {
                T target = targetClazz.newInstance();
                targets.add(target);
                convert(obj, target);
            } catch (Exception e) {
                return;
            }
        }
    }

    private static void convert(Object source, Object target) {
        copyProperties(source, target);
    }


    private final class DateToStringConverter implements Converter<Date, String> {

        private DateFormat df;

        private DateToStringConverter(String format) {
            df = new SimpleDateFormat(format);
        }

        @Override
        public String convert(Date date) {
            return df.format(date);
        }
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static Object convertForProperty(Wrapper wrapper, Object object, Object value,
            String property) {
        Object result;
        if (Objects.isNull(wrapper)) {
            result = null;
        } else {
            wrapper.setWrappendInstance(object);
            result = wrapper.getBeanWrapper().convertForProperty(value, property);
        }
        return result;
    }

    private static Object copyProperties(Object source, Object target) {
        Wrapper wrapper = new MyBeanCopy().new Wrapper(source);
        copyProperties(wrapper, source, target);
        return target;
    }

    private static void copyProperties(Wrapper wrapper, Object source, Object target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = BeanUtils
                        .getPropertyDescriptor(source.getClass(), targetPd.getName());
                try {
                    if (sourcePd.getWriteMethod() != null) {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }

                        Object value = readMethod.invoke(source);

                        //判断是否类型一致
                        if (value != null && !(targetPd.getPropertyType().isInstance(value))) {
                            //数据转换
                            value = convertForProperty(wrapper, target, value, targetPd.getName());
                        }
                        Method writeMethod = targetPd.getWriteMethod();
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    }
                } catch (Exception ex) {
                    throw new FatalBeanException("Could not copy properites from source to target",
                            ex);
                }
            }
        }
    }


    private final class Wrapper {

        private GenericConversionService conversion;
        private BeanWrapperImpl bean;

        private Wrapper(Object object) {
            conversion = initDefaultConversionService();
            bean = initDefaultWrapper(conversion, object);
        }

        private void setWrappendInstance(Object object) {
            bean.setWrappedInstance(object);
        }

        private GenericConversionService initDefaultConversionService() {
            GenericConversionService conversionService = new DefaultConversionService();
            conversionService.addConverter(new DateToStringConverter(DATE_FORMAT));
            return conversionService;
        }

        private BeanWrapperImpl initDefaultWrapper(
                @SuppressWarnings("hiding") ConversionService conversion,
                Object object) {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
            beanWrapper.setConversionService(conversion);
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            beanWrapper.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
            return beanWrapper;
        }

        private BeanWrapperImpl getBeanWrapper() {
            return bean;
        }
    }
}
