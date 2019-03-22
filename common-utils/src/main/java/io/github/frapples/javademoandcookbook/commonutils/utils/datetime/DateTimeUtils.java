package io.github.frapples.javademoandcookbook.commonutils.utils.datetime;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间日期操作。
 * @see DateUtils 对Date日期进行操作，如加天数、减天数等。以及格式化为String。
 * @see DateFormatUtils 解析String表示的日期为Date。
 *
 * https://blinkfox.github.io/2018/09/29/hou-duan/java/commons/apache-commons-lang-zhi-ri-qi-shi-jian-gong-ju-lei/
 */
public class DateTimeUtils {

    public static Optional<Date> parseDate(String data, String parsePatterns) {
        try {
            Date date = DateUtils.parseDate(data, parsePatterns);
            return Optional.ofNullable(date);
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static Date unixEpoch2Date(long epoch) {
        return new Date(epoch * 1000L);
    }

    /**
     * 1. java8的特性，可得到以秒为单位的时间戳
     * @return
     */
    public static long timestamp() {
        return Instant.now().getEpochSecond();
    }
}
