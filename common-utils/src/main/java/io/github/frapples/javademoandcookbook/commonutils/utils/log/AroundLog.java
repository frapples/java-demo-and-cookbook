package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.slf4j.event.Level;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/24
 */
@Documented
@Target(value = { ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundLog {

    String enterFormat() default "Enter function ${functionName}";
    String exitFormat() default "Exit function ${functionName}";

    Level level() default Level.DEBUG;
}
