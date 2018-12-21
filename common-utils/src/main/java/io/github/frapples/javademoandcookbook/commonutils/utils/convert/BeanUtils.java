package io.github.frapples.javademoandcookbook.commonutils.utils.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

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

    public static String getterNameToFieldName(String getterName) {
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

    public static String fieldNameToGetterName(String filedName) {
        if (filedName == null || filedName.isEmpty()) {
            return "";
        } else {
            return "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        }
    }

    public static String fieldNameToSetterName(String filedName) {
        if (filedName == null || filedName.isEmpty()) {
            return "";
        } else {
            return "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        }
    }

    public static Method fieldToGetter(Field field) throws NoSuchMethodException {
        String getterName = fieldNameToGetterName(field.getName());
        return field.getDeclaringClass().getMethod(getterName);
    }

    public static Method fieldToSetter(Field field) throws NoSuchMethodException {
        String getterName = fieldNameToSetterName(field.getName());
        return field.getDeclaringClass().getMethod(getterName, field.getType());
    }

    /**
     * 通过java反射获取一个字段的值
     */
    public static <T> T fieldGet(Object object, String filedName, Class<T> fieldType) {
        String getterName = fieldNameToGetterName(filedName);
        try {
            Method getter = object.getClass().getMethod(getterName);
            return (T)getter.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * 通过java反射设置一个字段的值
     */
    public static <T> void fieldSet(Object object, String field, Class<T> fieldType, T value) {
        String setterName = fieldNameToSetterName(field);
        try {
            Method setter = object.getClass().getMethod(setterName, fieldType);
            setter.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    public static List<Field> getFieldsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
        return FieldUtils.getFieldsListWithAnnotation(cls, annotationCls);
    }

    public static List<Field> getAllFieldsList(Class<?> clazz) {
        return FieldUtils.getAllFieldsList(clazz);
    }

    /**
     * 获取bean的所有成员变量定义。
     * 如果继承链上有同名成员变量，顺序是：以子类靠前，父类靠后的顺序。
     */
    public static Map<String, List<Field>> getAllFields(Class<?> clazz) {
        Map<String, List<Field>> allFields = new HashMap<>();
        for (Class<?> c = clazz; c!= null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                List<Field> fields = allFields.getOrDefault(f.getName(), new ArrayList<>());
                fields.add(f);
                allFields.put(f.getName(), fields);
            }
        }
        return allFields;
    }

    /**
     * 获取bean的所有成员变量中，被指定注解修饰的成员变量定义。
     * 如果继承链上有同名成员变量，顺序是：以子类靠前，父类靠后的顺序。
     */
    public static Map<String, List<Field>> getAllFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        Map<String, List<Field>> allFields = getAllFields(clazz);
        for (List<Field> fields : allFields.values()) {
            fields.removeIf(field -> !field.isAnnotationPresent(annotation));
        }
        allFields.values().removeIf(List::isEmpty);
        return allFields;
    }
}
