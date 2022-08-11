package com.nobblecrafts.learn.redis.dbs.service.impl;

import com.nobblecrafts.learn.redis.dbs.domain.RedisVote;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;
import com.nobblecrafts.learn.redis.dbs.exception.BadRequestException;
import com.nobblecrafts.learn.redis.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.redis.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.redis.dbs.service.VotationService;
import com.nobblecrafts.learn.redis.dbs.util.RedisVoteBuilder;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociateService implements VotationService {

  private final RedisVoteRepository repository;
  private final RedisAgendaRepository agendaRepository;
  private final String userInfoUrl = "https://user-info.herokuapp.com/users/";

  private final RedisVoteBuilder redisVoteBuilder = new RedisVoteBuilder();

  @Override
  @Transactional
  public RedisVote vote(Vote vote) {
    var now = System.currentTimeMillis();

    log.info("\n\n\nAll Agendas: {}\n\n\n", agendaRepository.findAll());

    var agenda = agendaRepository.findById(vote.getAgendaId())
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));

    var start = agenda.getStart().getTime();
    var end = agenda.getEnd().getTime();

    if (now < start)
      throw new BadRequestException("Essa agenda não está aberta");

    if (now > end)
      throw new BadRequestException("Essa agenda já está fechada");

    var redisVote = redisVoteBuilder.buildRedisVote(vote.getVote(), vote.getAssociateId(), agenda)
        .orElseThrow(() -> new InternalError("Algum erro aconteceu tentando salvar o voto"));

    // var info = restTemplate.getForObject(userInfoUrl + vote.getAssociateCpf(),
    // UserInfoDTO.class);
    // log.info("after info");
    // if (info.getStatus().equalsIgnoreCase("UNABLE_TO_VOTE"))
    // throw new BadRequestException("Este CPF não está apto para votar");

    return repository.save(redisVote);
  }

}
