package io.github.frapples.springbootcookbook.web.config;

import io.github.frapples.springbootcookbook.web.resolver.UserIdArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig {

    /*
    * 1. 自定义argumentResolver示例。通过自定义WebMvcConfigurer，重写addArgumentResolvers方法，在其中添加参数解析器
    * 2. 在Controller的参数上使用自定义注解，当http请求进入时调用controller前，框架会调用自定义的参数解析器以获取调用controller所需要的参数
    * */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new UserIdArgumentResolver());
            }
        };
    }
}
