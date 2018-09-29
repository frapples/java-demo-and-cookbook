package io.github.frapples.javademoandcookbook.demoandcase.javacase.threadsafe;


/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/21
 */

public class VisibilityCase {
    private int a = 0, b = 0;
    private int x = 0, y = 0;

    private void demo() throws InterruptedException {
        Thread p1 = new Thread(() -> {
            a = 1;
            x = b;
        });


        Thread p2 = new Thread(() -> {
            b = 1;
            y = a;
        });

        p1.start();
        p2.start();

        p1.join();
        p2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        int[][]count = new int[2][2];

        int loop = 100000;
        for (int i = 0; i < loop; i++) {
            VisibilityCase v = new VisibilityCase();
            v.demo();
            if (i % 500 == 0) {
                System.gc();
            }
            count[v.x][v.y]++;
        }

        System.out.printf("Total: %s\n0, 0: %s\n0, 1: %s\n1, 0: %s\n1, 1: %s\n",
            loop, count[0][0], count[0][1], count[1][0], count[1][1]);
    }
}

