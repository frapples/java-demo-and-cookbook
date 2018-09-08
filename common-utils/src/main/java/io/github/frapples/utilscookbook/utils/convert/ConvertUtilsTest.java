package io.github.frapples.utilscookbook.utils.convert;

import com.google.common.collect.Maps;
import io.github.frapples.utilscookbook.utils.pojo.Point;
import java.util.Map;


public class ConvertUtilsTest {

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("x", 1);
        map.put("y", 2);
        System.out.println(ConvertUtils.convertFromMap(map, Point.class));
        System.out.println(ConvertUtils.convertToMap(new Point(3, 4, 0)));
    }

}
