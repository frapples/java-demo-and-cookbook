package io.github.frapples.springbootcookbook.web.config;

import java.util.regex.Pattern;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class RegexpMatcherCorsFilter extends CorsFilter {

    public RegexpMatcherCorsFilter(String... regexp) {
        super(configurationSource(regexp));
    }

    private static UrlBasedCorsConfigurationSource configurationSource(String... regexp) {
        CorsConfiguration config = new RegexpMatcherCorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        for (String s : regexp) {
            config.addAllowedOrigin(s);
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    static class RegexpMatcherCorsConfiguration extends CorsConfiguration {
        public RegexpMatcherCorsConfiguration() {
        }

        @Override
        public String checkOrigin(String requestOrigin) {
            if (!StringUtils.hasText(requestOrigin) || ObjectUtils.isEmpty(super.getAllowedOrigins())) {
                return null;
            }

            if (super.getAllowedOrigins().contains(ALL)) {
                return super.getAllowCredentials() != Boolean.TRUE ? ALL : requestOrigin;
            }

            for (String allowedOrigin : super.getAllowedOrigins()) {
                if (Pattern.compile(allowedOrigin).matcher(requestOrigin).matches()) {
                    return requestOrigin;
                }
            }
            return null;
        }
    }
}
