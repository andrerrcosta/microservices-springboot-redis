package com.nobblecrafts.learn.redis.admin.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AMQPConfiguration {

    private final AMQPConfigData config;

    @Bean(name = "schedulerExchange")
    public TopicExchange schedulerTopicExchange() {
        log.info("Scheduler Exchange created: " + config.getSchedulerExchange());
        return ExchangeBuilder.topicExchange(config.getSchedulerExchange()).durable(true).build();
    }

    @Bean(name = "adminExchange")
    public TopicExchange adminTopicExchange() {
        log.info("Admin Exchange created: " + config.getAdminExchange());
        return ExchangeBuilder.topicExchange(config.getAdminExchange()).durable(true).build();
    }

    @Bean
    public Queue agendaQueue() {
        return QueueBuilder.durable(config.getAdminQueue()).ttl((int) Duration.ofHours(1).toMillis()).maxLength(25000).build();
    }

    /**
     * Sem o binder você não irá conseguir associar a fila ao exchange
     */
    @Bean
    public Binding handleAnalyticsReport(final Queue panelQueue, @Qualifier("adminExchange") final TopicExchange topicExchange) {
        return BindingBuilder
                .bind(panelQueue)
                .to(topicExchange)
                .with(config.getAdminRoutingKey());
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(
            final MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
