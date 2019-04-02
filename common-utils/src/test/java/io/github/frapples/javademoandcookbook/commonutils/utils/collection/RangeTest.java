package io.github.frapples.javademoandcookbook.commonutils.utils.collection;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/4/2
 */
class RangeTest {

    @Test
    void range() {
        test(-1, -1, new Long[] {});
        test(-1, 0, new Long[] { -1L });
        test(-1, 5, new Long[] {-1L, 0L, 1L, 2L, 3L, 4L});


        test(0, 0, 3, new Long[] {});
        test(0, 1, 3, new Long[] { 0L });
        test(0, 2, 3, new Long[] { 0L });
        test(0, 3, 3, new Long[] { 0L });
        test(0, 4, 3, new Long[] { 0L, 3L });
        test(0, 8, 3, new Long[] {0L, 3L, 6L});


        test(-1, -2, new Long[] {});

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            test(-1, 10, -3, new Long[] {});
        });
    }

    void test(long start, long end, Long[] excepted) {
        Long[] actual = Iterables.toArray(Range.range(start, end), Long.class);
        Assertions.assertEquals(Arrays.asList(excepted), Arrays.asList(actual));
    }

    void test(long start, long end, int step, Long[] excepted) {
        Long[] actual = Iterables.toArray(Range.range(start, end, step), Long.class);
        Assertions.assertEquals(Arrays.asList(excepted), Arrays.asList(actual));
    }

}