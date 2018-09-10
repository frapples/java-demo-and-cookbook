package io.github.frapples.javademoandcookbook.demoandcase.demo.introspector;

import static java.lang.System.out;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

public class PropertyEditorDemo {

    public static Point convert(Map<String, String> param) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Point point = Point.of();
        BeanInfo beanInfo = Introspector.getBeanInfo(Point.class);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            Class<?> propertyType = pd.getPropertyType();
            if (propertyType == Class.class) {
            } else if (propertyType == String.class) {
                pd.getWriteMethod().invoke(point, param.get(pd.getName()));
            } else {
                PropertyEditor edit = PropertyEditorManager.findEditor(propertyType);
                if (edit != null) {
                    edit.setAsText("test");
                    pd.getWriteMethod().invoke(point, edit.getValue());
                } else {
                    out.println("No edit: " + pd.getName());
                }
            }
        }
        return point;
    }

    public static void main(String[] args) {
        PropertyEditorManager.registerEditor(Date.class, DateEditor.class);
    }
}


class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(new Date());
    }
}