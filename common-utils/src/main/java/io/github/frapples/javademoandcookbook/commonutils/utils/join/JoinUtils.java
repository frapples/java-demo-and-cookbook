package io.github.frapples.javademoandcookbook.commonutils.utils.join;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/13
 */
public class JoinUtils {


    @FunctionalInterface
    interface Consumer3<A, B, C> {
        void invoke(A a, B b, C c);
    }

    private JoinUtils() {
        throw new UnsupportedOperationException();
    }

    public static <A, B, P> void leftJoin2(List<A> listA, List<B> listB,
        Function<A, P> getterA, Function<B, P> getterB, BiConsumer<A, B> mergeFunc) {
        Map<P, B> map = listB.stream().collect(Collectors.toMap(getterB, t -> t, (a, b) -> a));

        for (A itemA : listA) {
            P p = getterA.apply(itemA);
            if (p != null) {
                B itemB = map.get(p);
                if (itemB != null) {
                    mergeFunc.accept(itemA, itemB);
                }
            }
        }
    }

    public static <A, B, P, R> List<R> leftJoin(List<A> listA, List<B> listB,
        Function<A, P> getterA, Function<B, P> getterB,
        BiFunction<A, B, R> mergeFunc) {
        Map<P, B> map = listB.stream().collect(Collectors.toMap(getterB, t -> t, (a, b) -> a));

        return listA.stream().map((itemA) -> {
            P p = getterA.apply(itemA);
            return p != null ? mergeFunc.apply(itemA, map.get(p)) : null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <A, B, P, R> List<R> leftJoin(List<A> listA, List<B> listB,
        Function<A, P> getterA, Function<B, P> getterB,
        Supplier<R> resultNew,
        Consumer3<A, B, R> mergeFunc) {
        return leftJoin(listA, listB, getterA, getterB, (itemA, itemB) -> {
            R result = resultNew.get();
            mergeFunc.invoke(itemA, itemB, result);
            return result;
        });
    }
}
