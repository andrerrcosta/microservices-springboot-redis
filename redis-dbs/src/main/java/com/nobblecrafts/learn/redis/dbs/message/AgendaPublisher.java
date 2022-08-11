package com.nobblecrafts.learn.redis.dbs.message;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;

import com.nobblecrafts.learn.redis.message.AMQPPublisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgendaPublisher implements AMQPPublisher<AgendaDTO> {

  private final AmqpTemplate amqpTemplate;
  private final String exchangeName;
  private final String routingKey;

  @Autowired
  public AgendaPublisher(final AmqpTemplate amqpTemplate,
      @Value("${amqp.exchange.admin}") final String exchangeName,
      @Value("${amqp.routingkey.admin}") final String routingKey) {
    this.amqpTemplate = amqpTemplate;
    this.exchangeName = exchangeName;
    this.routingKey = routingKey;
  }

  @Override
  public void publish(final AgendaDTO agenda) {
    log.info("Trying to publish {}", agenda);
    try {
      amqpTemplate.convertAndSend(exchangeName, routingKey, agenda);
      log.info("ANALYTICS-PUBLISHER: message sent");
      log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, routingKey,
          agenda);
    } catch (Error e) {
      log.error("Error publishing agenda {}", e);
    }
  }

}
