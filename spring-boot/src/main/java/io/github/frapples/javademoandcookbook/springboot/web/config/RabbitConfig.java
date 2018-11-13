package io.github.frapples.javademoandcookbook.springboot.web.config;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */

@Configuration
public class RabbitConfig {

    @Autowired
    private Environment env;


    @Data
    public static class MQBizQueueConfig {
        private String exchangeType;
        private String exchangeName;
        private String queueName;
        private String routeKey;

        @SneakyThrows
        public static MQBizQueueConfig of(Environment env, String prefix) {
            MQBizQueueConfig config = new MQBizQueueConfig();
            config.setExchangeName(env.getProperty(prefix + ".exchange-name"));
            config.setExchangeType(env.getProperty(prefix + ".exchange-type"));
            config.setQueueName(env.getProperty(prefix + ".queue-name"));
            config.setRouteKey(env.getProperty(prefix + ".route-key"));
            return config;
        }
    }

    /**
     * 连接工厂
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public ConnectionFactory mainConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        // connectionFactory.setHost(env.getProperty("spring.rabbitmq.host"));
        // connectionFactory.setCacheMode(CacheMode.CHANNEL);//默认是CHANNEL
        // connectionFactory.setChannelCacheSize(
            // Integer.parseInt(env.getProperty("spring.rabbitmq.cachesize")));
        // 开启发布者确认
        // connectionFactory.setPublisherConfirms(true);
        // 开启发布者返回
        // connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }


    /**
     * 模板类，主要负责发送接收
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(mainConnectionFactory());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("mainConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        configDemoBiz(rabbitAdmin, MQBizQueueConfig.of(env, "mq.bizqueue.demobiz"));
        return rabbitAdmin;
    }

    private void configDemoBiz(RabbitAdmin rabbitAdmin, MQBizQueueConfig mqBizQueueConfig) {
        DirectExchange exchange = (DirectExchange) ExchangeBuilder
            .directExchange(mqBizQueueConfig.getExchangeName()).durable(true).build();
        rabbitAdmin.declareExchange(exchange);
        Queue queue = QueueBuilder.nonDurable(mqBizQueueConfig.getQueueName()).build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
            .to(exchange)
            .with(mqBizQueueConfig.getRouteKey()));
    }
}
