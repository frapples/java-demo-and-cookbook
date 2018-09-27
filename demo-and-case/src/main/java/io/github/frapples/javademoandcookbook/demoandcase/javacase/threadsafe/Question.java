package io.github.frapples.javademoandcookbook.demoandcase.javacase.threadsafe;

import io.github.frapples.javademoandcookbook.commonutils.utils.sequence.Range;
import java.util.ArrayDeque;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-27
 *
 * 描述：一个队列里存有顺序数据，0，1，2，3，4，5。。。
 * 有三个线程，希望他们依次轮流从队列里五个五个取数据来打印。
 */
public class Question {

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>(Range.rangeList(1, 100));

        Object condition = new Object();
        new PrintThread(0, queue, condition).start();
        new PrintThread(1, queue, condition).start();
        new PrintThread(2, queue, condition).start();
    }
}

@AllArgsConstructor
class PrintThread extends Thread {

    private int no;
    private final Queue<Integer> printedQueue;
    private final Object condition;

    @Override
    @SneakyThrows
    public void run() {
        synchronized (condition) {
            while (!printedQueue.isEmpty()) {
                int printed = printedQueue.peek();
                if (isShouldPrint(printed)) {
                    printedQueue.poll();
                    System.out.printf("Thread %d: %d\n", no, printed);
                } else {
                    condition.notifyAll();
                    condition.wait();
                }
                condition.notifyAll();
            }
        }
    }

    private boolean isShouldPrint(int n) {
        return (n - 1) / 5 % 3 == no;
    }
}