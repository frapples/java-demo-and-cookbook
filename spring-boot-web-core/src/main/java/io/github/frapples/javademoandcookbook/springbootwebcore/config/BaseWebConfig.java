package io.github.frapples.javademoandcookbook.springbootwebcore.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/5/16
 */
public class BaseWebConfig {

    @Configuration
    public static class HbbtvMimeMapping implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

        /**
         * 加载静态资源时，设置各资源文件的Content-Type 注意刷新浏览器缓存
         */
        @Override
        public void customize(ConfigurableServletWebServerFactory factory) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("css", "text/css; charset=utf-8");
            mappings.add("htm", "text/html; charset=utf-8");
            mappings.add("html", "text/html; charset=utf-8");
            mappings.add("js", "application/javascript; charset=utf-8");
            mappings.add("mjs", "application/javascript; charset=utf-8");
            factory.setMimeMappings(mappings);
        }
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
