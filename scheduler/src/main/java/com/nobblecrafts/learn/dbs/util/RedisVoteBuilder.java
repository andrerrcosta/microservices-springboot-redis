package com.nobblecrafts.learn.dbs.util;

import java.util.Optional;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.dbs.domain.RedisVote;

public class RedisVoteBuilder {

  public static Optional<RedisVote> buildRedisVote(String vote, Long associateId, RedisAgenda agenda) {

    if (agenda.getAssociates() == null)
      return Optional.empty();

    Boolean hasAssociate = agenda.getAssociates().stream()
        .anyMatch(a -> a == associateId);

    if (hasAssociate) {
      return Optional.of(RedisVote.builder()
          .agenda(agenda.getId())
          .associate(associateId)
          .vote(vote)
          .id(RedisVoteBuilder.buildVoteKey(associateId, agenda.getId()))
          .build());
    }
    return Optional.empty();
  }

  public static String buildVoteKey(Long associateId, Long agendaId) {
    return String.format("agenda-%d:%d", agendaId, associateId);
  }

  public static Long getIdFromRedisVote(RedisVote redisVote) {
    var split = redisVote.getId().split(":");
    return Long.parseLong(split[split.length - 1]);
  }
}
