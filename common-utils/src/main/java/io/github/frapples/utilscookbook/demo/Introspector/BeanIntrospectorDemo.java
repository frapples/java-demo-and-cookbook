package io.github.frapples.utilscookbook.demo.introspector;

import static java.lang.System.out;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class BeanIntrospectorDemo {

    private Point bean = Point.of(1, 2, "test");

    /**
     * 属性描述器
     */
    public void propertyDescriptorDemo() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor descriptor = new PropertyDescriptor("comment", Point.class);
        Object value = descriptor.getReadMethod().invoke(bean);
        out.printf("%s: %s\n", descriptor.getName(), value);
        out.printf("propertyType: %s\n", descriptor.getPropertyType());
    }

    public void beanInfoDemo() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Point.class);
        PropertyDescriptor[] pDescriptors = beanInfo.getPropertyDescriptors();
        out.printf("length: %s\n", pDescriptors.length);
        for (PropertyDescriptor pd : pDescriptors) {
            out.printf("%s: %s\n", pd.getName(), pd.getReadMethod().invoke(bean));
        }
    }

    public void beanInfoDemo2() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Point.class);
        MethodDescriptor[] mDescriptors = beanInfo.getMethodDescriptors();
        out.printf("length: %s\n", mDescriptors.length);
        for (MethodDescriptor m : mDescriptors) {
            out.printf("%s\n", m.getName());
        }

    }

    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        new BeanIntrospectorDemo().propertyDescriptorDemo();
        out.println("----------");
        new BeanIntrospectorDemo().beanInfoDemo();
        out.println("----------");
        new BeanIntrospectorDemo().beanInfoDemo2();
    }

}
