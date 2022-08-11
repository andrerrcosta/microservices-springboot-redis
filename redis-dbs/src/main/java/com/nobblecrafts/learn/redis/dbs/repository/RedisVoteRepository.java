package com.nobblecrafts.learn.redis.dbs.repository;

import java.util.List;

import com.nobblecrafts.learn.redis.dbs.domain.RedisVote;

import org.springframework.data.repository.CrudRepository;

public interface RedisVoteRepository extends CrudRepository<RedisVote, String> {

  List<RedisVote> findByAgenda(Long agenda);
}
