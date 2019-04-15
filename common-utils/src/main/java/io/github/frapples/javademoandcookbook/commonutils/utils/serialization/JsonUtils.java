package io.github.frapples.javademoandcookbook.commonutils.utils.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
@Slf4j
public class JsonUtils {

    /**
     * 美化json，便于优雅调试，提升debug体验
     */
    @SneakyThrows
    public static String beautifyJson(Object object) {
        if (object instanceof String) {
            log.debug("警告：JsonUtil.beautifyJson用于处理java对象而非json字符串，您真正想要的是否是JsonUtil.beautifyJsonString?");
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    /**
     * 美化json，便于优雅调试，提升debug体验
     */
    @SneakyThrows
    public static String beautifyJsonString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = mapper.readValue(json, HashMap.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }


}
