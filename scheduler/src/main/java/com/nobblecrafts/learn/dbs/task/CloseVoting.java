package com.nobblecrafts.learn.dbs.task;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.dbs.service.AgendaPublisher;
import com.nobblecrafts.learn.dbs.util.RedisVoteBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseVoting implements Runnable {

  private final RedisAgenda agenda;
  private final RedisAgendaRepository agendaRepository;
  private final RedisVoteRepository voteRepository;
  private final AgendaPublisher publisher;

  public CloseVoting(RedisAgenda agenda, RedisVoteRepository voteRepository, RedisAgendaRepository agendaRepository,
      AgendaPublisher publisher) {
    this.agenda = agenda;
    this.voteRepository = voteRepository;
    this.agendaRepository = agendaRepository;
    this.publisher = publisher;
  }

  @Override
  public void run() {
    log.info("Votação da agenda {} var ser encerrada", agenda);
    
    var allVotes = this.voteRepository.findAllById(agenda.getAssociates().stream()
        .map(a -> RedisVoteBuilder.buildVoteKey(a, agenda.getId()))
        .collect(Collectors.toList()));

    var all = voteRepository.findAll();
    log.info("All votes from repository {}", all);
    Map<Long, String> votes = new HashMap<>();
    allVotes.forEach(v -> votes.put(RedisVoteBuilder.getIdFromRedisVote(v), v.getVote()));
    agenda.setVotes(votes);
    agendaRepository.delete(agenda);
    voteRepository.deleteAll(allVotes);
    publisher.publishAgenda(agenda);
    log.info("Votação da agenda {} foi encerrada", agenda);
  }

}