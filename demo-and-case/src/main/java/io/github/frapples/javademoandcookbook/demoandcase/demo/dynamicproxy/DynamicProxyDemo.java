package io.github.frapples.javademoandcookbook.demoandcase.demo.dynamicproxy;

import com.google.common.reflect.Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


class RealObjectProxyUtils {

    public static NumerOperationInterface wrap(NumerOperationInterface real) {
        /* 这个函数只能传入接口, 因此被代理类需要实现接口 */
        return (NumerOperationInterface) Proxy.newProxyInstance(
            NumerOperationInterface.class.getClassLoader(),
            new Class[]{NumerOperationInterface.class},
            new RealObjectProxyHandler(real));
    }

    public static NumerOperationInterface wrap2(NumerOperationInterface real) {
        /* 使用guava提供的动态代理辅助函数，更简单的写法 */
        return Reflection.newProxy(NumerOperationInterface.class, new RealObjectProxyHandler(real));
    }
}

class RealObjectProxyHandler implements InvocationHandler {

    private Object proxied;

    public RealObjectProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.printf("调用函数%s\n", method.getName());
        return method.invoke(proxied, args);
    }

}

interface NumerOperationInterface {

    int add(int a, int b);
}

class RealObject implements NumerOperationInterface {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

