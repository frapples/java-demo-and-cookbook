package io.github.frapples.javademoandcookbook.demoandcase.javacase.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/25
 */
public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> f = executor.submit(ThreadInterruptDemo::prime);
        Thread.sleep(500);
        f.cancel(true);
        try {
            Boolean r = f.get();
            System.out.println(r);
        } finally {
            executor.shutdown();
        }
    }

    static boolean prime() {
        long num = 1000000033L;
        for (long i = 2; i < num; i++) {
            // 检测interrupt标记
            if (Thread.currentThread().isInterrupted()) {
                return false;
            }

            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
