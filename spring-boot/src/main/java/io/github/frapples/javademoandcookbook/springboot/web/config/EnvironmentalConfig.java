package io.github.frapples.javademoandcookbook.springboot.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentalConfig {
    @Getter
    @Value("${spring.profiles.active}")
    private String environment;
}
