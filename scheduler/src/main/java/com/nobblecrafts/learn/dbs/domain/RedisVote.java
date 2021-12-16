package com.nobblecrafts.learn.dbs.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@RedisHash("Vote")
@Data
@Builder
@With
public class RedisVote implements Serializable {

  @Id
  String id;

  Long agenda;

  String vote;

  Long associate;

  @Transient
  String associateCpf;

}
