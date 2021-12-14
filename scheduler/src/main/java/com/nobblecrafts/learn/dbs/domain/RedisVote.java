package com.nobblecrafts.learn.dbs.domain;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@RedisHash("Vote")
@Data
@Builder
public class RedisVote implements Serializable {

  @Id
  String id;

  Long agenda;

  String vote;

  Long associate;

}
