package io.github.frapples.javademoandcookbook.springboot.common.tools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 *
 * 简单的分布式自旋锁，基于Redis。
 * 不能重入。
 * 没有严谨测试过，仅限于自测时替代Redission的分布式锁用一下。
 */

public class RedisSpinLock implements Lock {

    private final static long UNLOCK = 0;

    private final static long TIMEOUT = 10 * 60 * 1000;

    private final RedisAtomicLong redisAtomicLong;

    public RedisSpinLock(RedisConnectionFactory factory, String name) {
        redisAtomicLong = new RedisAtomicLong(name, factory);

    }

    @Override
    public void lock() {
        try {
            boolean success = tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
            if (!success) {
                throw new RuntimeException("超时");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    @Override
    public boolean tryLock() {
        long ms = System.currentTimeMillis();
        long cur = redisAtomicLong.get();
        if (cur == UNLOCK) {
            return redisAtomicLong.compareAndSet(UNLOCK, ms);
        } else if (ms - cur >= TIMEOUT) {
            return redisAtomicLong.compareAndSet(cur, ms);
        } else {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        long start = System.currentTimeMillis();
        long timeOut = unit.toMillis(time);
        while (!tryLock()) {
            long current = System.currentTimeMillis();
            long wait = current - start;
            if (wait >= timeOut) {
                return false;
            }
            sleepAccordingWaitingMs(wait);
        }
        return true;
    }

    private void sleepAccordingWaitingMs(long wait) throws InterruptedException {
        long sleep;
        if (wait < 10) {
            sleep = 10;
        } else if (wait < 100) {
            sleep = wait / 2;
        } else if (wait < 1000) {
            sleep = wait / 10;
        } else if (wait < 60 * 1000) {
            sleep = 1000;
        } else if (wait < 10 * 50 * 1000) {
            sleep = 2000;
        } else {
            sleep = 10 * 1000;
        }
        Thread.sleep(sleep);
    }

    @Override
    public void unlock() {
        redisAtomicLong.set(UNLOCK);
    }

    @NotNull
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
