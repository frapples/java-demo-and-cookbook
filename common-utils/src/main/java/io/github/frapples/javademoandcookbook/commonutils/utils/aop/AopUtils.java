package io.github.frapples.javademoandcookbook.commonutils.utils.aop;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AopUtils {

    public static <T> T proxy(T object, List<Class<?>> aspectClasses) {
        AspectJProxyFactory factory = new AspectJProxyFactory(object);
        factory.setProxyTargetClass(true);
        aspectClasses.forEach(factory::addAspect);
        return factory.getProxy();
    }


}
