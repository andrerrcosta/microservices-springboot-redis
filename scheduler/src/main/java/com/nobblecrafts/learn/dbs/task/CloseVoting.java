package com.nobblecrafts.learn.dbs.task;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.dbs.service.AgendaPublisher;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;
import com.nobblecrafts.learn.dbs.util.RedisVoteBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseVoting implements Runnable {

  private final RedisAgenda agenda;
  private final RedisAgendaRepository agendaRepository;
  private final RedisVoteRepository voteRepository;
  private final AgendaPublisher publisher;
  private final AgendaMapper mapper;

  public CloseVoting(RedisAgenda agenda, RedisVoteRepository voteRepository, RedisAgendaRepository agendaRepository,
      AgendaPublisher publisher) {
    this.agenda = agenda;
    this.voteRepository = voteRepository;
    this.agendaRepository = agendaRepository;
    this.publisher = publisher;
    this.mapper = new AgendaMapper();
  }

  @Override
  public void run() {
    var allVotes = this.voteRepository.findAllById(agenda.getAssociates().stream()
        .map(a -> RedisVoteBuilder.buildVoteKey(a, agenda.getId()))
        .collect(Collectors.toList()));

    Map<Long, String> votes = new HashMap<>();
    allVotes.forEach(v -> votes.put(RedisVoteBuilder.getIdFromRedisVote(v), v.getVote()));
    agenda.setVotes(votes);
    agendaRepository.delete(agenda);
    voteRepository.deleteAll(allVotes);
    publisher.publishAgenda(mapper.convertFromEntityToDTO(agenda));
    log.info("Votação da agenda {} foi encerrada", agenda);
  }

}