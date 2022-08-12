package com.nobblecrafts.learn.redis.dbs.integration;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;
import com.nobblecrafts.learn.redis.dbs.scheduler.VoteScheduler;
import com.nobblecrafts.learn.redis.dbs.util.AgendaSupplier;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

/**
 * Eu modifiquei algumas coisas e tenho que consertar essa classe
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@Slf4j
public class AssociateControllerTest {

  @Autowired
  private TestRestTemplate template;
  @Autowired
  private VoteScheduler scheduler;
  private final AgendaSupplier supplier = new AgendaSupplier();

  @Test
  public void A1_voteOnce() throws InterruptedException {
    var agenda = createAgenda();
    var vote = supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId()).stream().findFirst().get();
    log.info("\n\nVote is: {}\n\n", vote);
    Thread.sleep(1200);
    var response = template.exchange("/client/vote", HttpMethod.POST, new HttpEntity<>(vote), Vote.class);
    log.info("\nResponse -> {}", response.getStatusCode());
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void B1_voteMany() throws InterruptedException {
    var agenda = createAgenda();
    var votes = supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId());
    log.info("\n\nVotes are: {}\n\n", votes);
    Thread.sleep(1200);
    var responses = votes.stream()
        .map(v -> template.exchange("/client/vote", HttpMethod.POST, new HttpEntity<>(v), Vote.class))
        .collect(Collectors.toList());
    Assertions.assertThat(responses).allMatch(r -> r.getStatusCode() == HttpStatus.OK);
  }

  private AgendaDTO createAgenda() {
    var agenda = supplier.createAgenda(1L, 1000L);
    log.info("\n\nAgenda is: {}\n\n", agenda);
    scheduler.schedule(agenda);
    return agenda;
  }
}
