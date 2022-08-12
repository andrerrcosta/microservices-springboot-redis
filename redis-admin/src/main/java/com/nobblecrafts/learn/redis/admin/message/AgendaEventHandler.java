package com.nobblecrafts.learn.redis.admin.message;

import com.nobblecrafts.learn.redis.admin.service.impl.SystemServiceImpl;
import com.nobblecrafts.learn.redis.event.EventHandler;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgendaEventHandler implements EventHandler<AgendaDTO> {

    private final SystemServiceImpl service;

    @Override
    @RabbitListener(queues = "${amqp-config.admin-queue}")
    public void handle(final AgendaDTO dto) {
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
