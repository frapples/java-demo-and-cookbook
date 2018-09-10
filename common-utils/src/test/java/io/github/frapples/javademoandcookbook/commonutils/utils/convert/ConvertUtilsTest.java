package io.github.frapples.javademoandcookbook.commonutils.utils.convert;

import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/10
 */

@Slf4j
class ConvertUtilsTest {

    @Test
    void convert() {
        Map<String, Object> map = this.createMap();
        Point point = ConvertUtils.convertFromMap(map, Point.class);
        Map<String, Object> map2 = ConvertUtils.convertToMap(Point.of(3, 4, "test"));
        log.info("{}", point);
        log.info("{}", map2);
    }

    private Map<String, Object> createMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", 1);
        map.put("y", 2);
        return map;
    }
}