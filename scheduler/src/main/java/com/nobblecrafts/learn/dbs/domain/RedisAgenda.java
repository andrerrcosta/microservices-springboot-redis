package com.nobblecrafts.learn.dbs.domain;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@RedisHash("Agenda")
@Builder
public class RedisAgenda {

  @Id
  Long id;
  Set<Long> associates;
  Map<Long, String> votes;
  Date start;
  Date end;

}
