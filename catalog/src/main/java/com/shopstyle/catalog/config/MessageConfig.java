package com.shopstyle.catalog.config;

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

    @Bean
    public static MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue queue() {
        return new Queue(CART_QUEUE);
    }/*

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CART_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CART_ROUTINGKEY);
    }
*/
    @Bean
    public AmqpTemplate template(ConnectionFactory connection) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}