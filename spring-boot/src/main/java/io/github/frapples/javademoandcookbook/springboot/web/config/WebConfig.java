package io.github.frapples.javademoandcookbook.springboot.web.config;

import io.github.frapples.javademoandcookbook.springboot.web.resolver.UserIdArgumentResolver;
import java.util.List;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig {

    /**
    * 1. 自定义argumentResolver示例。通过自定义WebMvcConfigurer，重写addArgumentResolvers方法，在其中添加参数解析器
    * 2. 在Controller的参数上使用自定义注解，当http请求进入时调用controller前，框架会调用自定义的参数解析器以获取调用controller所需要的参数
    */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new UserIdArgumentResolver());
            }
        };
    }


    /**
    * 1. 演示了设置上传文件的大小，以下将上传文件大小限制为10M
    */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10L* 1024L * 1024L);
        return factory.createMultipartConfig();
    }

    /**
    * 1. 演示了RegexpMatcherCorsFilter的定义，并且使用它来实现跨域控制
    */
    @Bean
    public FilterRegistrationBean regexpMatcherCorsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        // 允许 *.xxx.com 跨域
        String[] allowOrigins = new String[]{"https?://(.*).xxx.com(\\:\\d+)?"};
        registrationBean.setFilter(new RegexpMatcherCorsFilter(allowOrigins));
        return registrationBean;
    }

    /**
     * Thanks for https://stackoverflow.com/questions/5928046/spring-mvc-utf-8-encoding
     * https://stackoverflow.com/questions/13504732/how-to-use-org-springframework-web-filter-characterencodingfilter-to-correct-cha
     * 解决static资源乱码问题, 解决HttpServletRequest.getParameter使用错误编码解码问题
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8", true);
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
