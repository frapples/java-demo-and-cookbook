package io.github.frapples.javademoandcookbook.commonutils.utils.basedatatype;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
public class EnumUtils {

    private EnumUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 从枚举类enumClass的枚举中，找出枚举中的某个字段与value相等的枚举。其中，字段由getter参数描述
     * @param enumClass 枚举类
     * @param getter 字段的getter
     * @param value
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T extends Enum, R> Optional<T> enumOf(Class<T> enumClass, Function<T, R> getter, R value) {
        T[] enums = enumClass.getEnumConstants();
        for (T item: enums) {
            if (Objects.equals(getter.apply(item), value)) {
                return Optional.ofNullable(item);
            }
        }
        return Optional.empty();
    }
}
