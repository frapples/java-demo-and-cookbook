package io.github.frapples.javademoandcookbook.commonutils.utils.bean;

import com.google.common.base.Preconditions;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 *
 * Bean相关的操作工具类、反射工具类一览：
 * @see FieldUtils
 * @see org.springframework.beans.BeanUtils
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
    @SuppressWarnings("unchecked")
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

    public static ReflectType reflectType = ReflectType.CGLIB;


    @SneakyThrows
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        Preconditions.checkNotNull(source, "Source must not be null");
        T target = targetClass.newInstance();
        return copyBean(source, target);
    }

    /**
     * 复制Bean对象。只复制两个对象中具有相同名称与类型的属性。
     */
    public static <T> T copyBean(Object source, T target) {
        Preconditions.checkNotNull(source, "Source must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");

        if (Objects.equals(reflectType, ReflectType.JAVA)) {
            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopier.copy(source, target, null);
        } else {
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        }
        return target;
    }

    public static <T> List<T> copyBean(List<?> source, Class<T> clazz) {
        source = ObjectUtils.defaultIfNull(source, Collections.emptyList());
        return source.stream().map(o -> copyBean(o, clazz)).collect(Collectors.toList());
    }

    @SneakyThrows
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        /* https://stackoverflow.com/questions/21720128/beanutils-converting-java-util-map-to-nested-bean */
            T bean = clazz.newInstance();
            return mapToBean(map, bean);
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (Objects.equals(reflectType, ReflectType.JAVA)) {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
            wrapper.setPropertyValues(new MutablePropertyValues(map), true, true);
        } else {
            BeanMap.create(bean).putAll(map);
        }
        return bean;
    }


    public enum ReflectType {
        JAVA,
        CGLIB;
    }

    @Builder(builderMethodName = "beanToMapHelper", buildMethodName = "beanToMap", builderClassName = "BeanToMapBuilder")
    public static Map<String, Object> beanToMap(Object bean, Boolean filterNull, ReflectType reflectType) {
        filterNull = ObjectUtils.defaultIfNull(filterNull, true);
        reflectType = ObjectUtils.defaultIfNull(reflectType,ReflectType.CGLIB);

        Map<String, Object> map;
        if (Objects.equals(reflectType, ReflectType.JAVA)) {
            map = beanToMapJavaImpl(bean);

        } else if (Objects.equals(reflectType, ReflectType.CGLIB)) {
            map = beanToMapCglibImpl(bean);
        } else {
            throw new IllegalArgumentException();

        }

        if (filterNull) {
            map.entrySet().removeIf(entry -> entry.getValue() == null);
        }
        return map;
    }

    private static Map<String, Object> beanToMapJavaImpl(Object bean) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        Map<String, Object> map = new HashMap<>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            map.put(name, wrapper.getPropertyValue(name));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> beanToMapCglibImpl(Object bean) {
        Map<String, Object> beanMap = BeanMap.create(bean);
        Map<String, Object> map = new HashMap<>(beanMap.size());
        for (Entry<String, Object> entry: beanMap.entrySet()) {
            map.put(entry.getKey() + "", entry.getValue());
        }
        return map;
    }

}
