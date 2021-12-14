package com.nobblecrafts.learn.admin.service;

import com.nobblecrafts.learn.admin.domain.AgendaDTO;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgendaEventHandler {

  private final SystemService service;

  @RabbitListener(queues = "${amqp.queue.admin}")
    public void handleAgenda(final AgendaDTO dto) {
        log.info("Scheduler Event Received {}", dto);
        try {
            log.info("\n\nAgenda received: {}\n\n", dto);
            service.closeAgenda(dto);
        } catch (final Exception e) {
            log.error("Error when trying to process Agenda {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
  
}
