package io.github.frapples.javademoandcookbook.commonutils.utils.concurrent;

import java.util.concurrent.atomic.LongAdder;

/**
 * 缓存Long值的LongAdder
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/4/2
 */

public class CacheLongAdder extends LongAdder {

    /**
     * 上次写时间
     */
    private volatile long lastModify;
    /**
     * 上次读时间
     */
    private volatile long lastRead;

    /**
     * 缓存的long值
     */
    private Long cacheSum = null;

    public CacheLongAdder() {
        this.lastModify = System.currentTimeMillis();
        this.lastRead = -1L;
    }

    @Override
    public void add(long x) {
        super.add(x);
        lastModify = System.currentTimeMillis();
    }

    /**
     * 获取累加结果。
     * @return 累加结果。结果可能具有误差。
     */
    @Override
    public long sum() {
        long lastRead = this.lastRead;
        long lastModify = this.lastModify;
        if (cacheSum == null || lastRead < lastModify) {
            cacheSum = super.sum();
            this.lastRead = System.currentTimeMillis();
        }
        return cacheSum;
    }
}
