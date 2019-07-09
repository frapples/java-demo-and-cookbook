package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import io.github.frapples.javademoandcookbook.commonutils.utils.aop.AopUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/25
 */
public class AroundLogAspectTest {

    @Slf4j
    public static class TestClass1 {

        @AroundLog(level = Level.INFO)
        void normal() {
            log.info("exec...");
        }

        @AroundLog(level = Level.INFO)
        void except() {
            int a = 1 / 0;
        }

        @AroundLog(level = Level.INFO, enterMessage = "${arg0}", exitFormat = "return: ${returnValue}")
        List<Integer> argsAndReturnValue(Pair<Integer, BigDecimal> pair) {
            return Arrays.asList(1, 2, 3, 4);
        }

        @AroundLog(level = Level.INFO, enterMessage = "${arg0}", exitFormat = "return: ${returnValue}")
        void argsNullAndVoid(Pair<Integer, BigDecimal> pair) {
        }

    }

    @Test
    public void test() {
        TestClass1 class1 = AopUtils.proxy(new TestClass1(), Collections.singletonList(AroundLogAspect.class));
        class1.normal();
        try {
            class1.except();
        } catch (Exception ignored) {
        }
        class1.argsAndReturnValue(Pair.of(1, BigDecimal.TEN));
        class1.argsNullAndVoid(null);
    }

}
