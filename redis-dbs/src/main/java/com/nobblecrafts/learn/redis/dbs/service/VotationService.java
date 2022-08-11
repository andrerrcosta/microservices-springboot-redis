package com.nobblecrafts.learn.redis.dbs.service;

import com.nobblecrafts.learn.redis.dbs.domain.RedisVote;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;

public interface VotationService {

  /**
   * Método para votação
   * 
   * @param vote
   * @param associateId
   * @param associateCpf
   * @param agendaId
   * @return
   */
  public RedisVote vote(Vote vote);

}
