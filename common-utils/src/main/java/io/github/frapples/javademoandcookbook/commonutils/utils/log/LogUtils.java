package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/24
 */
public class LogUtils {

    public static void log(Logger log, Level level, String format, Object... args) {
        switch (level) {
            case ERROR:
                log.error(format, args);
                break;
            case WARN:
                log.warn(format, args);
                break;
            case INFO:
                log.info(format, args);
                break;
            case DEBUG:
                log.debug(format, args);
                break;
            case TRACE:
                log.trace(format, args);
                break;
            default:
                throw new IllegalStateException("Illegal state");
        }
    }

    public static boolean isEnable(Logger log, Level level) {
        switch (level) {
            case ERROR:
                return log.isErrorEnabled();
            case WARN:
                return log.isWarnEnabled();
            case INFO:
                return log.isInfoEnabled();
            case DEBUG:
                return log.isDebugEnabled();
            case TRACE:
                return log.isTraceEnabled();
            default:
                throw new IllegalStateException("Illegal state");
        }
    }

}
