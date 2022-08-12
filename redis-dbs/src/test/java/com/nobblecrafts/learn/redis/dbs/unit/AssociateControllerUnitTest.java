package com.nobblecrafts.learn.redis.dbs.unit;

import java.util.List;
import java.util.Set;

import com.nobblecrafts.learn.redis.dbs.controller.AssociateController;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;
import com.nobblecrafts.learn.redis.dbs.service.VotationService;
import com.nobblecrafts.learn.redis.dbs.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AssociateControllerUnitTest {

  @InjectMocks
  private AssociateController controller;

  @Mock
  private VotationService service;
  private AgendaSupplier supplier = new AgendaSupplier();
  private Vote validVote;
  private Vote invalidVote;

  @Test
  void testValidVote() {
    validVote = supplier.createRandomValidVotes(Set.of(1L), 1L).stream().findFirst().get();
    log.info("\n\n\nvalidVote is {}\n\n\n", validVote);
    var res = this.controller.vote(validVote);
    Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(res.getBody()).isEqualTo(validVote);
  }

  /**
   * Este teste vai retornar ok mesmo sendo inválido porque testes unitários
   * não importam o contexto do spring e a anotação de @Valid do controller
   * precisa do contexto para validar a entrada.
   */
  @Test
  void testInvalidVote() {
    invalidVote = supplier.createRandomInvalidVotes(List.of(1L), 1L).get(0);
    log.info("\n\n\ninvalidVote is {}\n\n\n", invalidVote);
    var res = this.controller.vote(invalidVote);
    Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(res.getBody()).isEqualTo(invalidVote);
  }
}
