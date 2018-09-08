package io.github.frapples.utilscookbook.javacase.generics;


public class Generics {

    private void func(int arg) {
        System.out.println("int: " + arg);
    }

    private void func(Object arg) {
        System.out.println("Object: " + arg);
    }

    private <T> void wrap(T args) {
        func(args);
    }

    private void test() {
        func(1);
        wrap(1);
    }

    public static void main(String[] args) {
        new Generics().test();
    }

}

/*
interface Demo {
    // 以下两行编译时报错，因为泛型擦除后是一个函数
    public void print(List<Integer> objects);
    public void print(List<String> strings);
    // 可以考虑使用通配符
    public void print(List<? extends Object> list);
}

*/
