package com.nobblecrafts.learn.redis.admin.message;

import com.nobblecrafts.learn.redis.admin.config.AMQPConfigData;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.mapper.AgendaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import com.nobblecrafts.learn.redis.message.AMQPPublisher;

@Service
@Slf4j
public class AgendaPublisher implements AMQPPublisher<AgendaDTO> {

    private final AmqpTemplate amqpTemplate;

    private final AgendaMapper mapper = AgendaMapper.INSTANCE;
    private final AMQPConfigData config;

    public AgendaPublisher(AmqpTemplate amqpTemplate, AMQPConfigData config) {
        this.amqpTemplate = amqpTemplate;
        this.config = config;
    }

    @Override
    public void publish(final AgendaDTO dto) {
        log.info("Trying to publish {}.", dto);
        try {
            amqpTemplate.convertAndSend(config.getAdminExchange(), config.getAdminRoutingKey(), dto);
            log.info("ANALYTICS-PUBLISHER: message sent\nexchangeName: {}\nroutingKey: {}\nmessage: {}",
                    config.getAdminExchange(),
                    config.getAdminRoutingKey(),
                    dto);
        } catch (Error e) {
            log.error("Error publishing agenda {}", e);
        }
    }

}
