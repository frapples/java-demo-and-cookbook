package io.github.frapples.javademoandcookbook.commonutils.utils.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */

@Slf4j
class BeanUtilsTest {

    @Test
    public void getterToFiledName() {
        assertEquals("x", BeanUtils.getterNameToFieldName("getX"));
        assertEquals("id", BeanUtils.getterNameToFieldName("getId"));
        assertEquals("aliPay", BeanUtils.getterNameToFieldName("getAliPay"));
        assertNull(BeanUtils.getterNameToFieldName(null));
        assertEquals("", BeanUtils.getterNameToFieldName(""));
        assertEquals("", BeanUtils.getterNameToFieldName("get"));
        assertEquals("geTc", BeanUtils.getterNameToFieldName("geTc"));
    }

    @Test
    public void getAllFields() throws NoSuchFieldException {

        BigDecimal bigDecimal = new BigDecimal("1");

        class BasePoint {
            private int x;

            @NotNull
            private int y;

            private int z;
        }


        class Point extends BasePoint {
            private int x;

            @NotNull
            private int y;

            void test() {
                bigDecimal.toString();
            }
        }

        Map<String, List<Field>> allFields = BeanUtils.getAllFields(Point.class);
        assertEquals(new HashMap<String, List<Field>>() {{
            put("x", Arrays.asList(Point.class.getDeclaredField("x"), BasePoint.class.getDeclaredField("x")));
            put("y", Arrays.asList(Point.class.getDeclaredField("y"), BasePoint.class.getDeclaredField("y")));
            put("z", Arrays.asList(BasePoint.class.getDeclaredField("z")));
            put("this$0", Arrays.asList(Point.class.getDeclaredField("this$0"), BasePoint.class.getDeclaredField("this$0")));
            put("val$bigDecimal", Arrays.asList(Point.class.getDeclaredField("val$bigDecimal")));
        }}, allFields);

        Map<String, List<Field>> allFieldsWithNotNull = BeanUtils.getAllFieldsWithAnnotation(Point.class, NotNull.class);
        assertEquals(new HashMap<String, List<Field>>() {{
            put("y", Arrays.asList(Point.class.getDeclaredField("y"), BasePoint.class.getDeclaredField("y")));
        }}, allFieldsWithNotNull);
    }

    @Test
    void convert() {
        Map<String, Object> map = this.createMap();
        Point point = BeanUtils.mapToBean(map, Point.class);
        Map<String, Object> map2 = BeanUtils.beanToMapHelper().bean(Point.of(3, 4, "test")).beanToMap();
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