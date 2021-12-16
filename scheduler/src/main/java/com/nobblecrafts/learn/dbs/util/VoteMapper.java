package com.nobblecrafts.learn.dbs.util;

import com.nobblecrafts.learn.dbs.domain.RedisVote;
import com.nobblecrafts.learn.dbs.domain.Vote;

public class VoteMapper extends ModelMapper<RedisVote, Vote> {

  public VoteMapper() {
    super(VoteMapper::convertToVote, VoteMapper::convertToRedisVote);
    // TODO Auto-generated constructor stub
  }

  private static RedisVote convertToRedisVote(Vote vote) {
    return RedisVote.builder()
        .agenda(vote.getAgendaId())
        .associate(vote.getAssociateId())
        .associateCpf(vote.getAssociateCpf())
        .vote(vote.getVote())
        .build();
  }

  private static Vote convertToVote(RedisVote vote) {
    return Vote.builder()
        .agendaId(vote.getAgenda())
        .associateCpf(vote.getAssociateCpf())
        .associateId(vote.getAssociate())
        .vote(vote.getVote())
        .build();
  }

}
