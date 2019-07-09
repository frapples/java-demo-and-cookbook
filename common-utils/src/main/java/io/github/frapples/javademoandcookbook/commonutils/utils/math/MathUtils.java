package io.github.frapples.javademoandcookbook.commonutils.utils.math;

import com.google.common.annotations.Beta;
import io.github.frapples.javademoandcookbook.commonutils.utils.script.ScriptUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/7/9
 *
 * @see org.apache.commons.lang3.math.NumberUtils
 * @see org.apache.commons.lang3.math.Fraction
 */
public class MathUtils {
    /** 默认的除法精确度 */
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 精确加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和(BigDecimal)
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        v1 = ObjectUtils.defaultIfNull(v1, BigDecimal.ZERO);
        v2 = ObjectUtils.defaultIfNull(v2, BigDecimal.ZERO);
        return v1.add(v2);
    }

    public static BigDecimal add(BigDecimal v1, long v2) {
        return add(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal add(long v1, BigDecimal v2) {
        return add( BigDecimal.valueOf(v1), v2);
    }

    public static BigDecimal add(BigDecimal v1, double v2) {
        return add(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal add(double v1, BigDecimal v2) {
        return add( BigDecimal.valueOf(v1), v2);
    }

    public static BigDecimal add(BigDecimal v1, BigInteger v2) {
        return add(v1, new BigDecimal(v2));
    }

    public static BigDecimal add(BigInteger v1, BigDecimal v2) {
        return add(new BigDecimal(v1), v2);
    }

    /**
     * 精确减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差(BigDecimal)
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        v1 = ObjectUtils.defaultIfNull(v1, BigDecimal.ZERO);
        v2 = ObjectUtils.defaultIfNull(v2, BigDecimal.ZERO);
        return v1.subtract(v2);
    }

    public static BigDecimal sub(BigDecimal v1, long v2) {
        return sub(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal sub(BigDecimal v1, double v2) {
        return sub(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal sub(BigDecimal v1, BigInteger v2) {
        return sub(v1, new BigDecimal(v2));
    }

    public static BigDecimal sub(long v1, BigDecimal v2) {
        return sub(BigDecimal.valueOf(v1), v2);
    }

    public static BigDecimal sub(double v1, BigDecimal v2) {
        return sub(BigDecimal.valueOf(v1), v2);
    }


    public static BigDecimal sub(BigInteger v1, BigDecimal v2) {
        return sub(new BigDecimal(v1), v2);
    }

    /**
     * 精确乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积(BigDecimal)
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        v1 = ObjectUtils.defaultIfNull(v1, BigDecimal.ZERO);
        v2 = ObjectUtils.defaultIfNull(v2, BigDecimal.ZERO);
        return v1.multiply(v2);
    }

    public static BigDecimal mul(BigDecimal v1, long v2) {
        return mul(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal mul(BigDecimal v1, double v2) {
        return mul(v1, BigDecimal.valueOf(v2));
    }

    public static BigDecimal mul(BigDecimal v1, BigInteger v2) {
        return mul(v1, new BigDecimal(v2));
    }

    public static BigDecimal mul(long v1, BigDecimal v2) {
        return mul(BigDecimal.valueOf(v1), v2);
    }

    public static BigDecimal mul(double v1, BigDecimal v2) {
        return mul(BigDecimal.valueOf(v1), v2);
    }

    public static BigDecimal mul(BigInteger v1, BigDecimal v2) {
        return mul(new BigDecimal(v1), v2);
    }


    /**
     * ( 相对 )精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(BigDecimal)
     */

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指定精度 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        v1 = ObjectUtils.defaultIfNull(v1, BigDecimal.ZERO);
        v2 = ObjectUtils.defaultIfNull(v2, BigDecimal.ZERO);
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal div(BigDecimal v1, long v2, int scale) {
        return div(v1, BigDecimal.valueOf(v2), scale);
    }

    public static BigDecimal div(BigDecimal v1, double v2, int scale) {
        return div(v1, BigDecimal.valueOf(v2), scale);
    }

    public static BigDecimal div(BigDecimal v1, BigInteger v2, int scale) {
        return div(v1, new BigDecimal(v2), scale);
    }

    public static BigDecimal div(long v1, BigDecimal v2, int scale) {
        return div(BigDecimal.valueOf(v1), v2, scale);
    }

    public static BigDecimal div(double v1, BigDecimal v2, int scale) {
        return div(BigDecimal.valueOf(v1), v2, scale);
    }

    public static BigDecimal div(BigInteger v1, BigDecimal v2, int scale) {
        return div(new BigDecimal(v1), v2, scale);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 尝试使用脚本来编写表达式，以弥补java没有运算符重载的不足
     * 使用spring expression，问题在于无法设置除法的进度和舍入方式
     */
    @Beta
    public static BigDecimal eval(String spel, Map<String, Object> args) {
        return ScriptUtils.evalSpel(spel, args, BigDecimal.class);
    }


    public static <T> int maxForIndex(List<? extends T> list, Comparator<? super T> comp) {
        if (list == null || list.isEmpty()) {
            return -1;
        }

        int index = 0;
        T candidate = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            T item = list.get(i);
            if (comp.compare(item, candidate) > 0) {
                index = i;
                candidate = item;
            }
        }
        return index;
    }


    public static BigDecimal sum(List<Integer> list) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Integer item : list) {
            if (item != null) {
                sum = sum.add(BigDecimal.valueOf(item));
            }
        }
        return sum;
    }

    public static BigDecimal sumBigDecimal(List<BigDecimal> list) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal item : list) {
            if (item != null) {
                sum = sum.add(item);
            }
        }
        return sum;
    }
}
