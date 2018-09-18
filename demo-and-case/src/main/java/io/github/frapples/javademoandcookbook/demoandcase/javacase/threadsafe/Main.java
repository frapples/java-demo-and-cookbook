package io.github.frapples.javademoandcookbook.demoandcase.javacase.threadsafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/15
 *
 * 错误的代码。第一，holder变量可见性问题；第二，构造函数的this溢出问题。
 */
public class Main {

    private static Holder holder;

    public static void main(String[] args) throws InterruptedException {
        int i = 1000;
        while (i-- > 0) {
            (new Thread(Main::initHolder)).start();
            (new Thread(() -> holder.assertSanity())).start();
        }
    }

    private static void initHolder() {
        holder = new Holder(42);
    }
}

class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n) {
            throw new AssertionError("This statement is false");
        }
    }
}
