package io.github.frapples.utilscookbook.demo;

import com.google.common.reflect.Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
* 1. 这是java实现动态代理的小demo. https://troywu0.gitbooks.io/spark/content/%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86.html
* 2. https://www.zhihu.com/question/20794107
*
* 作用:
* 1. 动态代理. 虽然叫动态代理,其实更像动态装饰器. 在InvocationHandler接口的invoke方法中, 你可以控制代理方法中执行的代码
* 1. 传统静态代理写法要手动的把一个一个的函数代理出去, 代码量和工作量随业务庞大而庞大. 动态代理就能解决此问题
* 1. 你可以在invoke方法中, 执行被代理对象的方法前后执行额外的代码. 这就是实现AOP
* 1. 可你可以在invoke方法中, 通过通讯网络获取数据以实现对象行为, 这就是实现了远程调用
* */

public class DynamicProxyDemo {

    public static void main(String[] args) {
        NumerOperationInterface real = new RealObject();
        real = wrap(real);
        real.add(1, 2);
    }

    private static NumerOperationInterface wrap(NumerOperationInterface real) {
        /* 这个函数只能传入接口, 因此被代理类需要实现接口 */
        return (NumerOperationInterface) Proxy.newProxyInstance(
            NumerOperationInterface.class.getClassLoader(),
            new Class[]{NumerOperationInterface.class},
            new RealObjectProxyHandler(real));
    }
}



/*
* 1. 使用guava提供的动态代理辅助函数，更简单的写法
*
* */

class GuavaDynamicProxyDemo {
    public static void main(String[] args) {
        NumerOperationInterface real = new RealObject();
        real = Reflection.newProxy(NumerOperationInterface.class, new RealObjectProxyHandler(real));
        System.out.println(real.add(1, 3));
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

