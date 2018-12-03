package io.github.frapples.javademoandcookbook.commonutils.utils.convert;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
class BeanUtilsTest {

    @Test
    public void getterToFiledName() {
        assertEquals("x", BeanUtils.getterToFiledName("getX"));
        assertEquals("id", BeanUtils.getterToFiledName("getId"));
        assertEquals("aliPay", BeanUtils.getterToFiledName("getAliPay"));
        assertNull(BeanUtils.getterToFiledName(null));
        assertEquals("", BeanUtils.getterToFiledName(""));
        assertEquals("", BeanUtils.getterToFiledName("get"));
        assertEquals("geTc", BeanUtils.getterToFiledName("geTc"));
    }
}