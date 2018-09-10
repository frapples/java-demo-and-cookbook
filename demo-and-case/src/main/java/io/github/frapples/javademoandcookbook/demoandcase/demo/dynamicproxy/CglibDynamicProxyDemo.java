package io.github.frapples.javademoandcookbook.demoandcase.demo.dynamicproxy;

import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibDynamicProxyDemo {

    public static void main(String[] args) {
        Point p = PointProxy.wrap(Point.class);
        p.setX(10);
        System.out.println(p.getX());
    }

}

class PointProxy implements MethodInterceptor {

    public static <T> T wrap(Class<T> proxiedClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxiedClass);
        enhancer.setCallback(new PointProxy());
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(method.getName());
        return proxy.invokeSuper(obj, args);
    }
}
