package com.nobblecrafts.learn.redis.admin.message;

import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.mapper.AgendaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.nobblecrafts.learn.redis.message.AMQPPublisher;

@Service
@Slf4j
public class AgendaPublisher implements AMQPPublisher<AgendaDTO> {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;
    private final String routingKey;
    private final AgendaMapper mapper;

    @Autowired
    public AgendaPublisher(final AmqpTemplate amqpTemplate,
                           @Value("${amqp.exchange.scheduler}") final String exchangeName,
                           @Value("${amqp.routingkey.scheduler}") final String routingKey, AgendaMapper mapper) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.mapper = AgendaMapper.INSTANCE;
    }

    @Override
    public void publish(final AgendaDTO dto) {
        log.info("Trying to publish {}.", dto);
        try {
            amqpTemplate.convertAndSend(exchangeName, routingKey, dto);
            log.info("ANALYTICS-PUBLISHER: message sent");
            log.info("\nexchangeName: {}\nroutingKey: {}\nmessage: {}", exchangeName, routingKey,
                    dto);
        } catch (Error e) {
            log.error("Error publishing agenda {}", e);
        }
    }

}
