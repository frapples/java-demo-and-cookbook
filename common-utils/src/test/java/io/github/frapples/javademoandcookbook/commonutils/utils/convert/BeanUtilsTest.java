package io.github.frapples.javademoandcookbook.commonutils.utils.convert;

import static org.junit.jupiter.api.Assertions.*;

import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import lombok.experimental.Helper;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
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
}