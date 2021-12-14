package com.nobblecrafts.learn.dbs.service;

import com.nobblecrafts.learn.dbs.domain.RedisVote;
import com.nobblecrafts.learn.dbs.domain.Vote;

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
