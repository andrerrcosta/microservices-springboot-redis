package com.nobblecrafts.learn.admin.service;

import java.util.Date;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.admin.util.AgendaMapper;

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
      @Value("${amqp.exchange.scheduler}") final String exchangeName,
      @Value("${amqp.routingkey.scheduler}") final String routingKey) {
    this.amqpTemplate = amqpTemplate;
    this.exchangeName = exchangeName;
    this.routingKey = routingKey;
    this.mapper = new AgendaMapper();
  }

  public void publishAgenda(final Agenda agenda) {
    log.info("Trying to publish {}.", agenda);
    try {
      amqpTemplate.convertAndSend(exchangeName, routingKey, mapper.convertFromEntityToDto(agenda));
      log.info("ANALYTICS-PUBLISHER: message sent");
      log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, routingKey,
          agenda);
    } catch (Error e) {
      log.error("Error publishing agenda {}", e);
    }
  }

}
