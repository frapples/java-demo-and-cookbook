package io.github.frapples.springbootcookbook.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentalConfig {
    @Getter
    @Value("${spring.application.environment}")
    private String environment;
}
