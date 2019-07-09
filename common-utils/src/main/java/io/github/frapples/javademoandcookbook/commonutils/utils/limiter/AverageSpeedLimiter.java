package io.github.frapples.javademoandcookbook.commonutils.utils.limiter;

import java.util.concurrent.TimeUnit;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/4
 *
 * 一个简单的平均流量限流器，使用周期基数法实现。
 * 如果需要限制峰值QPS，使用guava的RateLimiter
 * @see com.google.common.util.concurrent.RateLimiter
 *
 * 没有在多线程环境下测过，可能有bug
 */
@Slf4j
@ToString
public class AverageSpeedLimiter {

    private final long periodMs;
    private final int qps;

    private volatile long periodStart;
    private long permitsCurrentPeriod = 0;
    private final Object lock = new Object();


    public AverageSpeedLimiter(long periodTime, TimeUnit timeUnit, int qps) {
        this(timeUnit.toMillis(periodTime), qps);
    }

    /**
     *
     * @param periodMs 计数周期
     * @param qps 平均qps
     */
    public AverageSpeedLimiter(long periodMs, int qps) {
        this.periodMs = periodMs;
        this.qps = qps;

        this.reset();
    }

    public double acquire() {
        return acquire(1);
    }

    public double acquire(int permits) {
        int remain = permits;
        long totalSleepMs = 0;
        do {
            remain = this.tryAcquire(remain);
            long now = System.currentTimeMillis();
            if (remain != 0) {
                if (permitsCurrentPeriod == 0) {
                    long sleepMs = periodStart + periodMs - now + 1;
                    sleep(sleepMs);
                    totalSleepMs += sleepMs;
                } else {
                    log.error("错误状态: {}，请求permits：{}", this, permits);
                    break;
                }
            }

        } while (remain > 0);

        return (double)totalSleepMs / 1000;
    }

    private int tryAcquire(int permits) {
        long now = System.currentTimeMillis();
        if (now - periodStart > periodMs) {
            this.reset();
        }

        synchronized (lock) {
            int usedPermits = (int) Math.min(this.permitsCurrentPeriod, permits);
            this.permitsCurrentPeriod -= usedPermits;
            return permits - usedPermits;
        }
    }

    private void reset() {
        periodStart = System.currentTimeMillis();
        synchronized (lock) {
            permitsCurrentPeriod = qps * periodMs / 1000;
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
