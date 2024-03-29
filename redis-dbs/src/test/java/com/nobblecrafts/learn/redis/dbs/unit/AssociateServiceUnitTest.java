package com.nobblecrafts.learn.redis.dbs.unit;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;
import com.nobblecrafts.learn.redis.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.redis.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.redis.dbs.service.impl.AssociateService;
import com.nobblecrafts.learn.redis.dbs.mapper.AgendaMapper;
import com.nobblecrafts.learn.redis.dbs.util.AgendaSupplier;
import com.nobblecrafts.learn.redis.dbs.mapper.VoteMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Slf4j
class AssociateServiceUnitTest {

  @InjectMocks
  private AssociateService service;

  @Mock
  private RedisAgendaRepository agendaRepository;
  @Mock
  private RedisVoteRepository voteRepository;

  private AgendaSupplier supplier = new AgendaSupplier();
  @Autowired
  private AgendaMapper agendaMapper = AgendaMapper.INSTANCE;
  @Autowired
  private VoteMapper voteMapper = VoteMapper.INSTANCE;
  private AgendaDTO agenda;
  private Vote validVote;
  private Vote invalidVote;

  @BeforeEach
  void setup() {
    agenda = supplier.createAgenda(1L, 0L);
    agenda.setEnd(new Date(agenda.getStart().getTime() + 60000));
    validVote = supplier.createRandomValidVotes(Set.of(agenda.
            getAssociates()
                    .stream().findFirst().get()), agenda.getId()).stream().findFirst().get();
    invalidVote = supplier.createRandomInvalidVotes(List.of(agenda
            .getAssociates().stream().findFirst().get()), agenda.getId()).get(0);
  }

  @Test
  void testValidVote() {

    BDDMockito.when(agendaRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(agendaMapper.toRedisAgenda(agenda)));

    BDDMockito.when(voteRepository.save(ArgumentMatchers.any()))
        .thenReturn(voteMapper.toRedisVote(validVote).withId("1"));

    log.info("\n\n\nvalidVote is {}\n\n\n", validVote);
    var res = this.service.vote(validVote);

    Assertions.assertThat(res).isEqualTo(voteMapper.toRedisVote(validVote).withId("1"));
  }

  /**
   * Esta possibilidade na verdade não existe porquê ele irá retornar
   * a excessão {@link DataIntegrityViolationException} porque o voto está vindo
   * do controller.
   */
  @Test
  void testInvalidVote() {

    BDDMockito.when(agendaRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(agendaMapper.toRedisAgenda(agenda)));

    // BDDMockito.when(voteRepository.save(ArgumentMatchers.any()))
    //     .thenReturn(voteMapper.convertFromDTOToEntity(invalidVote).withId("1"));

    log.info("\n\n\ninvalidVote is {}\n\n\n", invalidVote);
    Assertions.assertThatExceptionOfType(InternalError.class)
        .isThrownBy(() -> service.vote(invalidVote));

    // Assertions.assertThat(res).isEqualTo(voteMapper.convertFromDTOToEntity(invalidVote).withId("1"));
  }
}
