package io.github.frapples.javademoandcookbook.commonutils.utils.asserts;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/22
 */
@Slf4j
public class Asserts {

    public static final Mode MODE = Mode.ALERT;

    public enum Mode {
        /**
         * DEBUG模式下，检查断言，并同时以异常和日志的形式告知。
         */
        DEBUG,
        /**
         * ALERT模式下，检查断言，只以日志的形式告知。
         */
        ALERT,
        /**
         * IGNORE模式下，忽略断言检查。
         */
        IGNORE
    };

    private static final String PREFIX = "断言失败：";

    private Asserts() {
        throw new UnsupportedOperationException();
    }

    public static void assertTrue(Supplier<Boolean> predicate, Supplier<String> msgSupplier) {
        if (!MODE.equals(Mode.IGNORE) && !predicate.get()) {
            error(PREFIX + msgSupplier.get());
        }
    }
    public static void assertTrue(Supplier<Boolean> predicate, String format, Object... args) {
        if (!MODE.equals(Mode.IGNORE) && !predicate.get()) {
            error(PREFIX + String.format(format, args));
        }
    }

    public static void assertTrue(boolean cond, String format, Object... args) {
        if (!MODE.equals(Mode.IGNORE) && !cond) {
            error(PREFIX + String.format(format, args));
        }
    }

    public static void assertNotNull(Object obj, String format, Object... args) {
        assertTrue(obj != null, format, args);
    }

    public static void assertNotNull(Object obj, Supplier<String> msgSupplier) {
        assertTrue(() -> obj != null, msgSupplier);
    }

    private static void error(String msg) {
        if (MODE.equals(Mode.DEBUG)) {
            throw new AssertionError(msg);
        }
        log.error(msg);
    }
}
