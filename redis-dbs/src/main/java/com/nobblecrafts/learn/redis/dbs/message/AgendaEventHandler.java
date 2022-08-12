package com.nobblecrafts.learn.redis.dbs.message;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;

import com.nobblecrafts.learn.redis.dbs.scheduler.VoteScheduler;
import com.nobblecrafts.learn.redis.event.EventHandler;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AgendaEventHandler implements EventHandler<AgendaDTO> {

    private final VoteScheduler service;
    @Override
    @RabbitListener(queues = "${amqp.queue.scheduler}")
    public void handle(final AgendaDTO dto) {
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
