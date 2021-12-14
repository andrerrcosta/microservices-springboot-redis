package com.nobblecrafts.learn.dbs.service;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AgendaEventHandler {

    private final SchedulerService service;

    @RabbitListener(queues = "${amqp.queue.scheduler}")
    public void handleAgenda(final AgendaDTO dto) {
        log.info("Scheduler Event Received {}", dto);
        try {
            log.info("\n\nAgenda received: {}.\n\n", dto);
            service.schedule(dto);
        } catch (final Exception e) {
            log.error("Error when trying to process Agenda {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
