package io.github.frapples.javademoandcookbook.demoandcase.demo.dynamicproxy;


/**
 * 1. 这是java实现动态代理的小demo. https://troywu0.gitbooks.io/spark/content/%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86.html
 * 2. https://www.zhihu.com/question/20794107
 *
 * 作用:
 * 1. 动态代理. 虽然叫动态代理,其实更像动态装饰器. 在InvocationHandler接口的invoke方法中, 你可以控制代理方法中执行的代码
 * 1. 传统静态代理写法要手动的把一个一个的函数代理出去, 代码量和工作量随业务庞大而庞大. 动态代理就能解决此问题
 * 1. 你可以在invoke方法中, 执行被代理对象的方法前后执行额外的代码. 这就是实现AOP
 * 1. 可你可以在invoke方法中, 通过通讯网络获取数据以实现对象行为, 这就是实现了远程调用
 * @author Frapples
 **/
public class DynamicProxyDemoTest {

    public static void main(String[] args) {
        NumerOperationInterface real = new RealObject();
        NumerOperationInterface proxied = RealObjectProxyUtils.wrap(real);
        proxied.add(1, 2);

        proxied = RealObjectProxyUtils.wrap2(real);
        proxied.add(1, 2);
    }

}

