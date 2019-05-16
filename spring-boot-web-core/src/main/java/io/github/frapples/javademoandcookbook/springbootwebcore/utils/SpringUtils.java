package io.github.frapples.javademoandcookbook.springbootwebcore.utils;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
1. SpringUtils被定义成Bean，并且实现ApplicationContextAware接口。spring容器在初始化时把容器的context环境注入。
因此，通过此方法可以得到容器的context环境
*/
@Component
public class SpringUtils implements ApplicationContextAware {

    @Getter
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
