package io.github.frapples.javademoandcookbook.commonutils.utils.limiter;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/25
 */
public class AverageSpeedLimiterTest {

    @Test
    public void acquire() {
        AverageSpeedLimiter limiter = new AverageSpeedLimiter(100, TimeUnit.MILLISECONDS, 100);
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 5; i++) {
            limiter.acquire(10);
        }
        long ms = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        double excepted = (double) ((5 * 10) / 10 - 1) * 100;
        System.out.printf("%d, %g\n", ms, excepted);
        assertTrue(ms >= excepted);

        limiter.acquire(1);

        ms = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        excepted = (double) ((5 * 10) / 10) * 100;
        System.out.printf("%d, %g\n", ms, excepted);
        assertTrue(ms >= excepted);
    }

    @Test
    public void testAcquire() {
        AverageSpeedLimiter limiter = new AverageSpeedLimiter(100, TimeUnit.MILLISECONDS, 100);

        Stopwatch stopwatch = Stopwatch.createStarted();
        limiter.acquire(500);
        long ms = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        double excepted = (double) (500 / 10 - 1) * 100;
        System.out.printf("%d, %g\n", ms, excepted);
        assertTrue(ms >= excepted);

        limiter.acquire(1);

        ms = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        excepted = (double) (500 / 10) * 100;
        System.out.printf("%d, %g\n", ms, excepted);
        assertTrue(ms >= excepted);
    }
}
