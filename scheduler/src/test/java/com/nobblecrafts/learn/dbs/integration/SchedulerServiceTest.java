package com.nobblecrafts.learn.dbs.integration;

import com.nobblecrafts.learn.dbs.domain.Vote;
import com.nobblecrafts.learn.dbs.service.SchedulerService;
import com.nobblecrafts.learn.dbs.service.VotationService;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Eu modifiquei algumas coisas e tenho que consertar essa classe
 */
@SpringBootTest
@Slf4j
public class SchedulerServiceTest {

  @Autowired
  private SchedulerService service;
  @Autowired
  private VotationService votationService;
  private AgendaSupplier supplier = new AgendaSupplier();
  @Autowired
  private RestTemplate template;

  // @Test
  @DisplayName("testCorrectVotation")
  public void testCorrectVotation() {
    var supplier = new AgendaSupplier();
    var agenda = supplier.createAgenda(1L, 0L);
    log.info("AGENDA SUPPLIED {}", agenda);
    service.schedule(agenda);

    try {
      Thread.sleep(2000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    var votes = supplier.createRandomValidVotes(agenda.getAssociates(), agenda.getId());
    log.info("VOTES SUPPLIED {}", votes);

    votes.forEach(vote -> template.postForEntity("http://localhost:8082/client/vote", vote, Vote.class));

  }

}
