package io.github.frapples.javademoandcookbook.commonutils.utils.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-28
 */

public class Range {

    private Range() {
        throw new UnsupportedOperationException();
    }

    public static List<Integer> rangeList(int start, int end) {
        return rangeList(start, end, 1);
    }

    public static List<Integer> rangeList(int start, int end, int step) {
        if (step == 0 || (end - start) * step < 0) {
            throw new IllegalArgumentException();
        }

        // TODO: Lazy implementation
        ArrayList<Integer> list = new ArrayList<>((end - start) / start);
        for (int i = start; i < end; i += step) {
            list.add(i);
        }
        return list;
    }

}
