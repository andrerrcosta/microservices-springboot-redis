package com.nobblecrafts.learn.redis.zlogger.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange logsExchange(@Value("${amqp.exchange.logs}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue logsQueue(@Value("${amqp.queue.logs}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding logsBinding(final Queue logsQueue, final TopicExchange logsExchange) {
        return BindingBuilder.bind(logsQueue).to(logsExchange).with("#");
    }

}
