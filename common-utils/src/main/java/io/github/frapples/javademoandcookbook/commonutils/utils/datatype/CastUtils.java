package io.github.frapples.javademoandcookbook.commonutils.utils.datatype;

import com.google.common.primitives.Ints;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/18
 */
public class CastUtils {

    private CastUtils() {
    }

    public static Integer castToInt(Long n) {
        return n == null ? null : Ints.checkedCast(n);
    }

    public static Integer castToInt(String s) {
        return s == null ? null : Integer.valueOf(s);
    }

}
