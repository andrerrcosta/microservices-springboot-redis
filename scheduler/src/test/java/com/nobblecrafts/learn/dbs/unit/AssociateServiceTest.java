package com.nobblecrafts.learn.dbs.unit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.domain.Vote;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.dbs.service.AssociateService;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;
import com.nobblecrafts.learn.dbs.util.VoteMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Slf4j
public class AssociateServiceTest {

  @InjectMocks
  private AssociateService service;

  @Mock
  private RedisAgendaRepository agendaRepository;
  @Mock
  private RedisVoteRepository voteRepository;

  private AgendaSupplier supplier = new AgendaSupplier();
  private AgendaMapper agendaMapper = new AgendaMapper();
  private VoteMapper voteMapper = new VoteMapper();
  private AgendaDTO agenda;
  private Vote validVote;
  private Vote invalidVote;

  @BeforeEach
  public void setup() {
    agenda = supplier.createAgenda(1L, 0L);
    agenda.setEnd(new Date(agenda.getStart().getTime() + 60000));
    validVote = supplier.createRandomValidVotes(List.of(agenda.getAssociates().get(0)), agenda.getId()).get(0);
    invalidVote = supplier.createRandomInvalidVotes(List.of(agenda.getAssociates().get(0)), agenda.getId()).get(0);
  }

  @Test
  public void testValidVote() {

    BDDMockito.when(agendaRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(agendaMapper.convertFromDTOToEntity(agenda)));

    BDDMockito.when(voteRepository.save(ArgumentMatchers.any()))
        .thenReturn(voteMapper.convertFromDTOToEntity(validVote).withId("1"));

    log.info("\n\n\nvalidVote is {}\n\n\n", validVote);
    var res = this.service.vote(validVote);

    Assertions.assertThat(res).isEqualTo(voteMapper.convertFromDTOToEntity(validVote).withId("1"));
  }

  /**
   * Esta possibilidade na verdade não existe porquê ele irá retornar
   * a excessão {@link DataIntegrityViolationException} porque o voto está vindo
   * do controller.
   */
  @Test
  public void testInvalidVote() {

    BDDMockito.when(agendaRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(agendaMapper.convertFromDTOToEntity(agenda)));

    BDDMockito.when(voteRepository.save(ArgumentMatchers.any()))
        .thenReturn(voteMapper.convertFromDTOToEntity(invalidVote).withId("1"));

    log.info("\n\n\ninvalidVote is {}\n\n\n", invalidVote);
    Assertions.assertThatExceptionOfType(InternalError.class)
        .isThrownBy(() -> service.vote(invalidVote));

    // Assertions.assertThat(res).isEqualTo(voteMapper.convertFromDTOToEntity(invalidVote).withId("1"));
  }
}
