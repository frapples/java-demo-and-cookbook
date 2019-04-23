package io.github.frapples.javademoandcookbook.commonutils.utils.thread;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.val;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/25
 */
class TraceExecutorServiceDecoratorTest {

    @Test
    void submit() throws InterruptedException, ExecutionException {
        ExecutorService pool = new TraceExecutorServiceDecorator(Executors.newFixedThreadPool(2));

        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int iFinal = i;
            futures.add(pool.submit(() -> 100 / iFinal));
        }

        for (val f : futures) {
            f.get();
        }
    }
}
