package com.nobblecrafts.learn.redis.dbs.repository;

import com.nobblecrafts.learn.redis.dbs.domain.RedisAgenda;

import org.springframework.data.repository.CrudRepository;

public interface RedisAgendaRepository extends CrudRepository<RedisAgenda, Long> {
  
}
