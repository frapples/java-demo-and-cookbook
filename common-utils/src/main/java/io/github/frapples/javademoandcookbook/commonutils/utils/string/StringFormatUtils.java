package io.github.frapples.javademoandcookbook.commonutils.utils.string;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/24
 */
public class StringFormatUtils {

    String format(String s, Map<String, Object> strings) {
        StringSubstitutor sub = new StringSubstitutor(strings, "{", "}");
        return sub.replace(s);
    }
}
