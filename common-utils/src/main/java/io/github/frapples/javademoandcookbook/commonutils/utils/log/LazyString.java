package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import lombok.SneakyThrows;
import org.jooq.lambda.fi.util.function.CheckedSupplier;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/25
 */

public class LazyString {
    private final CheckedSupplier<?> stringSupplier;


    LazyString(final CheckedSupplier<?> stringSupplier) {
        this.stringSupplier = stringSupplier;
    }

    @Override
    @SneakyThrows
    public String toString() {
        return String.valueOf(stringSupplier.get());
    }
}
