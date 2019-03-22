package io.github.frapples.javademoandcookbook.commonutils.utils.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/22
 */
public class CollectionUtils {

    public static <E, T> Map<E, T> toIndexMap(Collection<? extends T> collection, Function<? super T, ? extends E> transform) {
        return collection.stream().collect(Collectors.toMap(transform, t -> t, (v1, v2) -> v1));
    }

    public static <E, T> List<E> map(Collection<? extends T> collection, Function<? super T, ? extends E> transform) {
        return collection.stream().map(transform).collect(Collectors.toList());
    }

    public static <K, V, T> Map<K, T> mapValues(Map<? extends K, ? extends V> map, BiFunction<? super K, ? super V, ? extends T> transform) {
        HashMap<K, T> newMap = new HashMap<>();
        map.forEach((k, v) -> {
            newMap.put(k, transform.apply(k, v));
        });
        return newMap;

    }

    public static <E, T> List<E> map(Collection<? extends T> collection, BiFunction<? super T, Integer, ? extends E> transform) {
        List<E> list = new ArrayList<>();
        int i = 0;
        for (T it : collection) {
            E r = transform.apply(it, i);
            list.add(r);
            i++;
        }
        return list;
    }

    public static <E, T> Optional<T> find(Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().filter(predicate).findAny();
    }

    public static <T> boolean some(Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().anyMatch(predicate);
    }

    public static <T> boolean every(Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().allMatch(predicate);
    }

    public static <T> List<T> filter(Collection<? extends T> collection, Predicate<? super T> predicate) {
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }


    public static <E, T> Set<E> mapToSet(Collection<? extends T> collection, Function<? super T, ? extends E> getter) {
        return collection.stream().map(getter).collect(Collectors.toSet());
    }

    public static <E> NavigableSet<E> newSet(Collection<? extends E> initValues, Comparator<? super E> comparator) {
        TreeSet<E> set = new TreeSet<>(comparator);
        set.addAll(initValues);
        return set;
    }
}

