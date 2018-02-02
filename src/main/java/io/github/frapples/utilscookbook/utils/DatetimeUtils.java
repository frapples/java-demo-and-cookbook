package io.github.frapples.utilscookbook.utils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DatetimeUtils {

    /*
     * 1. apache的DateUtils和DateFormatUtils提供了方便的时间解析和格式化函数
     * */
    public static Optional<Date> YyyymmddhhmmssToDate(String data) {
        try {
            Date date = DateUtils.parseDate(data, "yyyyMMddHHmmss");
            return Optional.ofNullable(date);
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    public static String DateToyyyymmddhhmmss(Date date) {
        return DateFormatUtils.format(date, "yyyyMMddHHmmss");
    }

    /*
    1. apache的DateUtils能很方便的对日期进行加1天等操作
    * */
    public static Date plusOneDay(Date date) {
        return DateUtils.addDays(date, 1);
    }

    /*
    1. java8的特性，可得到以秒为单位的时间戳
     *  */
    public static long timestamp() {
        return Instant.now().getEpochSecond();
    }
}
