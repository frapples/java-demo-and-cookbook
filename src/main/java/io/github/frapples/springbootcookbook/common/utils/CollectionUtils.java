package io.github.frapples.springbootcookbook.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/30
 */
public class CollectionUtils {
    public static <S, T> List<T> map(Collection<S> list, Function<? super S, ? extends T> func) {
        return list.stream().map(func).collect(Collectors.toList());
    }

    public static <S, T> Set<? extends T> mapToSet(Collection<S> list, Function<? super S, ? extends T> func) {
        return list.stream().map(func).collect(Collectors.toSet());
    }
}
