package com.nobblecrafts.learn.redis.dbs.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class RedisVote implements Serializable {

  @Id
  private String id;

  private Long agenda;

  private String vote;

  private Long associate;

  @Transient
  private String associateCpf;

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    RedisVote that = (RedisVote) obj;
    return id.equals(that.id);
  }

}
