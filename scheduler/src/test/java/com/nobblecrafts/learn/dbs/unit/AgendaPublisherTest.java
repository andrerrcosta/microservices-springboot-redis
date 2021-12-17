package com.nobblecrafts.learn.dbs.unit;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.service.AgendaPublisher;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AgendaPublisherTest {

  private AgendaPublisher publisher;
  private AgendaSupplier supplier = new AgendaSupplier();

  @Mock
  private AmqpTemplate template;

  @BeforeEach
  public void setup() {
    publisher = new AgendaPublisher(template, "test.exchange", "test.key");
  }

  @Test
  public void testPublisher() {
    var agenda = supplier.createAgenda(1L, 0L);
    var votes = new HashMap<Long, String>();
    supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId())
        .stream().map(v -> votes.put(v.getAssociateId(), v.getVote()));
    agenda.setVotes(votes);

    // when
    publisher.publishAgenda(agenda);

    // then
    var exchange = ArgumentCaptor.forClass(String.class);
    var routing = ArgumentCaptor.forClass(String.class);
    var event = ArgumentCaptor.forClass(AgendaDTO.class);

    verify(template).convertAndSend(exchange.capture(), routing.capture(), event.capture());
    then(exchange.getValue()).isEqualTo("test.exchange");
    then(routing.getValue()).isEqualTo("test.key");
    log.info("event.getValue() -> {}", event.getValue());
    then(event.getValue()).isEqualTo(agenda);

  }

}
