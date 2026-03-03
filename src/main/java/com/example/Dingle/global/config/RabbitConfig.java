package com.example.Dingle.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String EXCHANGE = "property.exchange";
    public static final String QUEUE = "property.created.queue";
    public static final String ROUTING_KEY = "property.created";

    @Bean
    public DirectExchange propertyExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue propertyCreatedQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding propertyCreatedBinding(Queue propertyCreatedQueue, DirectExchange propertyExchange) {
        return BindingBuilder.bind(propertyCreatedQueue)
                .to(propertyExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory cf,
            Jackson2JsonMessageConverter converter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setMessageConverter(converter);
        return factory;
    }
}
