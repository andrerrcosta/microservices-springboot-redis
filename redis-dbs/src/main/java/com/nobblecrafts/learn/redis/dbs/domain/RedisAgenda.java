package com.nobblecrafts.learn.redis.dbs.domain;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("Agenda")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RedisAgenda {

  @Id
  private Long id;
  private Set<Long> associates;
  private Map<Long, String> votes;
  private Date start;
  private Date end;

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    RedisAgenda that = (RedisAgenda) obj;
    return id.equals(that.id);
  }

}
