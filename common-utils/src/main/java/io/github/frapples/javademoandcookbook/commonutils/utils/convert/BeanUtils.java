package io.github.frapples.javademoandcookbook.commonutils.utils.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
public class BeanUtils {

    public static class ReflectionException extends RuntimeException {

        public ReflectionException(Throwable e) {
            super(e);
        }
    }

    private BeanUtils() {
    }

    public static String getterToFiledName(String getterName) {
        if (getterName == null) {
            return null;
        }

        if (Objects.equals(StringUtils.substring(getterName, 0, 3), "get")) {
            String name = StringUtils.substring(getterName, 3);
            return StringUtils.substring(name, 0, 1).toLowerCase() + StringUtils.substring(name, 1);
        } else {
            return getterName;
        }

    }

    /**
     * 通过java反射获取一个字段的值
     */
    public static Object getField(Object object, String filed) {
        String getterName = "get" + StringUtils.capitalize(filed);
        try {
            Method getter = object.getClass().getMethod(getterName);
            return getter.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * 通过java反射设置一个字段的值
     */
    public static void setField(Object object, String filed, Object value) {
        String setterName = "set" + StringUtils.capitalize(filed);
        try {
            Method setter = object.getClass().getMethod(setterName);
            setter.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * 获取bean的所有字段，包括父类字段
     * @param clazz
     * @return
     */
    public static List<Field> allFields(Class<?> clazz) {
        HashMap<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            Arrays.stream(clazz.getDeclaredFields()).forEach(f -> fields.putIfAbsent(f.getName(), f));
            clazz = clazz.getSuperclass();
        }
        return new ArrayList<>(fields.values());
    }

    public static List<Field> fieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> fields = allFields(clazz);
        fields.removeIf(field -> !field.isAnnotationPresent(annotation));
        return fields;
    }

}
