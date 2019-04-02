package io.github.frapples.javademoandcookbook.commonutils.utils.collection;

import java.util.Iterator;
import lombok.AllArgsConstructor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-28
 */

@AllArgsConstructor
public class Range implements Iterable<Long> {

    private Range() {
        throw new UnsupportedOperationException();
    }

    public static Range range(long start, long end) {
        return new Range(start, end, 1);
    }

    public static Range range(long start, long end, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("step不能为0");
        }

        if ((end - start) * step < 0) {
            throw new IllegalArgumentException("给入的step会导致无穷流");
        }
        return new Range(start, end, step);
    }

    private long start;
    private long end;
    private int step;

    @Override
    public Iterator<Long> iterator() {
        return new RangeIterator();
    }

    class RangeIterator implements Iterator<Long> {

        private long next = start;

        @Override
        public boolean hasNext() {
            return next < end;
        }

        @Override
        public Long next() {
            long next = this.next;
            this.next += step;
            return next;
        }
    }
}
