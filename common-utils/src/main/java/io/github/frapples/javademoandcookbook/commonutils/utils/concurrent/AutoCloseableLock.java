package io.github.frapples.javademoandcookbook.commonutils.utils.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/7/9
 *
 * Thanks for: https://stackoverflow.com/questions/6965731/are-locks-autocloseable
 */

public class AutoCloseableLock {

    public interface AutoCloseableLockContext extends AutoCloseable {

        @Override
        void close();
    }

    private Lock lock;
    private AutoCloseableLockContext autoCloseable;

    public AutoCloseableLock(Lock lock) {
        this.lock = lock;
        this.autoCloseable = this.lock::unlock;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    public Condition newCondition() {
        return this.lock.newCondition();
    }

    public boolean tryLock() {
        return this.lock.tryLock();
    }

    public void lockInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public AutoCloseableLockContext withLockInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
        return autoCloseable;
    }

    public AutoCloseableLockContext withLock() {
        this.lock.lock();
        return autoCloseable;
    }

    public AutoCloseableLockContext withTryLock(long time, TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!this.lock.tryLock(time, unit)) {
            throw new TimeoutException();
        }
        return autoCloseable;
    }
}
