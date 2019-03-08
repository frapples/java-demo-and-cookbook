package io.github.frapples.javademoandcookbook.springboot.web.config;

import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/8
 */

@Configuration
public class CloseConfig {

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory redisConnectionFactory() {
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(AmqpTemplate.class)
    public AmqpTemplate amqpTemplate() {
        return null;
    }

}
