package com.nobblecrafts.learn.dbs.service;

import com.nobblecrafts.learn.dbs.domain.RedisVote;
import com.nobblecrafts.learn.dbs.domain.Vote;
import com.nobblecrafts.learn.dbs.exception.BadRequestException;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.dbs.util.RedisVoteBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssociateService implements VotationService {

  private final RedisVoteRepository repository;
  private final RedisAgendaRepository agendaRepository;
  private final String userInfoUrl = "https://user-info.herokuapp.com/users/";
  private final RestTemplate restTemplate;

  @Override
  public RedisVote vote(Vote vote) {
    var now = System.currentTimeMillis();

    var agenda = agendaRepository.findById(vote.getAgendaId())
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));

    var start = agenda.getStart().getTime();
    var end = agenda.getEnd().getTime();


    if (now < start)
      throw new BadRequestException("Essa agenda não está aberta");
    if (now > end)
      throw new BadRequestException("Essa agenda já está fechada");
    var redisVote = RedisVoteBuilder.buildRedisVote(vote.getVote(), vote.getAssociateId(), agenda)
      .orElseThrow(() -> new InternalError("Algum erro aconteceu tentando salvar o voto"));


    // var info = restTemplate.getForObject(userInfoUrl + vote.getAssociateCpf(), UserInfoDTO.class);
    // log.info("after info");
    // if (info.getStatus().equalsIgnoreCase("UNABLE_TO_VOTE"))
    //   throw new BadRequestException("Este CPF não está apto para votar");
    
    return repository.save(redisVote);
  }

}
