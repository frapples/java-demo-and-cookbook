package io.github.frapples.utilscookbook.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValues;

public class ConvertUtils {

    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> convert(List source, Class<T> class_) {
        List<T> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (Object o : source) {
            result.add(convert(o, class_));
        }
        return result;
    }

    public static <T> T convertFromMap(Map map, Class<T> class_) {
        /* https://stackoverflow.com/questions/21720128/beanutils-converting-java-util-map-to-nested-bean */
        try {
            T bean = class_.newInstance();
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
            //wrapper.setPropertyValues(map);
            wrapper.setPropertyValues((PropertyValues)new MutablePropertyValues(map), true, true);
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> convertToMap(Object bean) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        Map<String, Object> map = new HashMap<>();
        PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            map.put(name, wrapper.getPropertyValue(name));
        }
        return map;
    }
}
