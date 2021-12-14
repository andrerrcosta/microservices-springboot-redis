package com.nobblecrafts.learn.dbs.service;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgendaPublisher {

  private final AmqpTemplate amqpTemplate;
  private final String exchangeName;
  private final String routingKey;
  private final AgendaMapper mapper;

  @Autowired
  public AgendaPublisher(final AmqpTemplate amqpTemplate,
      @Value("${amqp.exchange.admin}") final String exchangeName,
      @Value("${amqp.routingkey.admin}") final String routingKey) {
    this.amqpTemplate = amqpTemplate;
    this.exchangeName = exchangeName;
    this.routingKey = routingKey;
    this.mapper = new AgendaMapper();
  }

  public void publishAgenda(final RedisAgenda agenda) {
    log.info("Trying to publish {}", agenda);
    try {
      amqpTemplate.convertAndSend(exchangeName, routingKey, mapper.convertFromRedisToEntity(agenda));
      log.info("ANALYTICS-PUBLISHER: message sent");
      log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, routingKey,
          agenda);
    } catch (Error e) {
      log.error("Error publishing agenda {}", e);
    }
  }

}
