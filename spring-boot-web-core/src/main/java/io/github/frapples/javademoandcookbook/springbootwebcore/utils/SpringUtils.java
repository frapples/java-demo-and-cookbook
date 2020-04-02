package io.github.frapples.javademoandcookbook.springbootwebcore.utils;

import java.util.Properties;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
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

    /**
     * <code>
     *  XxxServiceConfig config = loadConfigBeanFromProperties("application-dev.yml", XxxServiceConfig.class);
     * </code>
     * https://stackoverflow.com/questions/26940583/can-i-manually-load-configurationproperties-without-the-spring-appcontext/39774535
     */
    @SneakyThrows
    private static <T> T loadConfigBeanFromProperties(String path, Class<T> clazz) {
        String prefix = clazz.getAnnotation(ConfigurationProperties.class).prefix();
        return loadConfigBeanFromProperties(path, prefix, clazz);
    }

    @SneakyThrows
    private static <T> T loadConfigBeanFromProperties(String path, String prefix, Class<T> clazz) {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(new ClassPathResource(path));

        Properties properties = factoryBean.getObject();
        ConfigurationPropertySource propertySource = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(propertySource);

        return binder.bind(prefix, clazz).get(); // same prefix as @ConfigurationProperties
    }
}
