package com.shopstyle.checkout.toSend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    public static final String CART_EXCHANGE = "cart.exchange";
    public static final String CART_ROUTINGKEY = "cart.routingkey";
    public static final String CART_QUEUE = "cart.queue";

    public static final String HISTORY_EXCHANGE = "history.exchange";
    public static final String HISTORY_ROUTINGKEY = "history.routingkey";
    public static final String HISTORY_QUEUE = "history.queue";
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue queue() {
        return new Queue(CART_QUEUE);
    }

    @Bean
    Queue queue1() {
        return new Queue(HISTORY_QUEUE);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CART_EXCHANGE);
    }

    @Bean
    public TopicExchange exchange1() {
        return new TopicExchange(HISTORY_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(CART_ROUTINGKEY);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(exchange1()).with(HISTORY_ROUTINGKEY);
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connection) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }
}
