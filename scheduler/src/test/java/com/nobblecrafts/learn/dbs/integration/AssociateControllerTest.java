package com.nobblecrafts.learn.dbs.integration;

import java.util.stream.Collectors;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.domain.Vote;
import com.nobblecrafts.learn.dbs.service.SchedulerService;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * Eu modifiquei algumas coisas e tenho que consertar essa classe
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AssociateControllerTest {

  @Autowired
  private TestRestTemplate template;
  @Autowired
  private SchedulerService scheduler;
  private final AgendaSupplier supplier = new AgendaSupplier();
  private AgendaDTO agenda;

  @BeforeEach
  public void setup() {
    createAgenda();
  }

  // @Test
  public void voteOnce() {
    var vote = supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId()).get(0);
    var response = template.exchange("/client/vote", HttpMethod.POST, new HttpEntity<>(vote), Vote.class);
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  // @Test
  public void voteMany() {
    var votes = supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId());
    var responses = votes.stream()
        .map(v -> template.exchange("/client/vote", HttpMethod.POST, new HttpEntity<>(v), Vote.class))
        .collect(Collectors.toList());
    Assertions.assertThat(responses).allMatch(r -> r.getStatusCode() == HttpStatus.OK);
  }

  private void createAgenda() {
    agenda = supplier.createAgenda(1L, 0L);
    scheduler.schedule(agenda);
  }

}
