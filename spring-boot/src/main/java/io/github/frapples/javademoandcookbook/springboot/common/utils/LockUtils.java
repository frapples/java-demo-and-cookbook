package io.github.frapples.javademoandcookbook.springboot.common.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public class LockUtils {

    private LockUtils() {
        throw new UnsupportedOperationException();
    }

    public static void withSynchronized(Lock lock, int time, TimeUnit timeUnit, Runnable continuation) throws InterruptedException, TimeoutException {
        if (lock.tryLock(time, timeUnit)) {
            try {
                continuation.run();
            } finally {
                lock.unlock();
            }
        } else {
            throw new TimeoutException("加锁超时");
        }
    }

    public static <T> T withSynchronized(Lock lock, int time, TimeUnit timeUnit, Supplier<T> continuation) throws InterruptedException, TimeoutException {
        if (lock.tryLock(time, timeUnit)) {
            try {
                return continuation.get();
            } finally {
                lock.unlock();
            }
        } else {
            throw new TimeoutException("加锁超时");
        }
    }
}
