package io.github.frapples.javademoandcookbook.commonutils.utils.functional;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */

public class StreamUtils {

    private StreamUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * zip两个列表组成的流，类似python的zip
     * @return 假设A为(a1, a2, a3)，B为(b1, b2, b3, b4)，则结果为((a1, b1), (a2, b2), (a3, b3))
     */
    public static <A, B> Stream<Pair<A, B>> zip(List<A> listA, List<B> listB) {
        int size = Math.min(listA.size(), listB.size());
        return IntStream.range(0, size)
            .mapToObj((i) ->
                ImmutablePair.of(listA.get(i), listB.get(i)));
    }


}
