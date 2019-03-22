package io.github.frapples.javademoandcookbook.commonutils.utils.basedatatype;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/24
 *
 * @see StringSubstitutor apache提供的字符串格式化工具。
 */
public class StringUtils {

    String format(String s, Map<String, Object> strings) {
        StringSubstitutor sub = new StringSubstitutor(strings, "{", "}");
        return sub.replace(s);
    }
}
