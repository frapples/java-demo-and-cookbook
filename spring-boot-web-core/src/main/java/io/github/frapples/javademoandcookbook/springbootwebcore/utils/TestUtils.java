package io.github.frapples.javademoandcookbook.springbootwebcore.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2020/3/18
 */
public class TestUtils {


    final static class AccessibilityChanger implements AutoCloseable {

        private final AccessibleObject accessibleObject;
        private final boolean wasAccessible;

        AccessibilityChanger(AccessibleObject accessibleObject) {
            this.accessibleObject = accessibleObject;
            this.wasAccessible = accessibleObject.isAccessible();
            accessibleObject.setAccessible(true);
        }

        @Override
        public void close() {
            try {
                accessibleObject.setAccessible(wasAccessible);
            } catch (Throwable t) {
                //ignore
            }
        }
    }


    public static void setField(Object target, Field field, Object value) {
        try (AccessibilityChanger ignored = new AccessibilityChanger(field)) {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access not authorized on field '" + field + "' of object '" + target + "' with value: '" + value + "'", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Wrong argument on field '" + field + "' of object '" + target + "' with value: '" + value + "', \n" +
                "reason : " + e.getMessage(), e);
        }
    }

    public static Object getField(Object target, Field field) {
        try (AccessibilityChanger ignored = new AccessibilityChanger(field)) {
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read state from field: " + field + ", on instance: " + target);
        }
    }

    public void setBeanProperty(final Object target, final Field field, final Object value) {
        String fieldName = field.getName();
        String setterName = "set"
            + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
            + fieldName.substring(1);

        Method writeMethod;
        try {
            writeMethod = target.getClass().getMethod(setterName, field.getType());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Problems setting value on object: [" + target + "] for property : [" + field.getName() + "], setter not found");
        }

        try (AccessibilityChanger ignored = new AccessibilityChanger(writeMethod)) {
            writeMethod.invoke(target, value);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Setter '" + writeMethod + "' of '" + target + "' with value '" + value + "' threw exception : '" + e.getTargetException() + "'", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access not authorized on field '" + field + "' of object '" + target + "' with value: '" + value + "'", e);
        }
    }
}
