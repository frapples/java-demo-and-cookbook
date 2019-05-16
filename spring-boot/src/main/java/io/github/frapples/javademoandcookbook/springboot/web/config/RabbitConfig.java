package io.github.frapples.javademoandcookbook.springboot.web.config;

import lombok.Data;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */

// @Configuration
public class RabbitConfig {

    @Data
    @ConfigurationProperties("mq.bizqueue")
    @Component
    public static class BizMQQueuesConfig {

        private MQQueueConfig demobiz;
    }

    @Data
    public static class MQQueueConfig {
        private String exchangeType;
        private String exchangeName;
        private String queueName;
        private String routeKey;
    }

    /**
     * 连接工厂
     */
    @Bean
    public ConnectionFactory mainConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        // connectionFactory.setCacheMode(CacheMode.CHANNEL);//默认是CHANNEL
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
    public RabbitAdmin rabbitAdmin(@Qualifier("mainConnectionFactory") ConnectionFactory connectionFactory, BizMQQueuesConfig bizMQQueuesConfig) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        this.configDemoBiz(rabbitAdmin, bizMQQueuesConfig.getDemobiz());
        return rabbitAdmin;
    }

    private void configDemoBiz(RabbitAdmin rabbitAdmin, MQQueueConfig mqQueueConfig) {
        DirectExchange exchange = (DirectExchange) ExchangeBuilder
            .directExchange(mqQueueConfig.getExchangeName()).durable(true).build();
        rabbitAdmin.declareExchange(exchange);
        Queue queue = QueueBuilder.nonDurable(mqQueueConfig.getQueueName()).build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
            .to(exchange)
            .with(mqQueueConfig.getRouteKey()));
    }
}
