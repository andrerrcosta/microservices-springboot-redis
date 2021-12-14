package com.nobblecrafts.learn.dbs.repository;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;

import org.springframework.data.repository.CrudRepository;

public interface RedisAgendaRepository extends CrudRepository<RedisAgenda, Long> {
  
}
