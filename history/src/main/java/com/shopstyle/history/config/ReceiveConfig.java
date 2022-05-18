package com.shopstyle.history.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiveConfig {

    public static final String HISTORY_EXCHANGE = "history.exchange";
    public static final String HISTORY_ROUTINGKEY = "history.routingkey";
    public static final String HISTORY_QUEUE = "history.queue";


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue queue1() {
        return new Queue(HISTORY_QUEUE);
    }

    @Bean
    public TopicExchange exchange1() {
        return new TopicExchange(HISTORY_EXCHANGE);
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connection) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }
}

