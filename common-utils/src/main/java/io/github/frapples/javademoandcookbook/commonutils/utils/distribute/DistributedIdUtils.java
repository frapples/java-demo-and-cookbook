package io.github.frapples.javademoandcookbook.commonutils.utils.distribute;

import java.util.UUID;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
public class DistributedIdUtils {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidCompressedTo32() {
        return uuid().replace("-", "");
    }

    public static String uuidCompressedTo26() {
        UUID uuid = UUID.randomUUID();
        long high = uuid.getMostSignificantBits();
        long low = uuid.getLeastSignificantBits();

        return Long.toUnsignedString(high, 32) + Long.toUnsignedString(low, 32);
    }
}
