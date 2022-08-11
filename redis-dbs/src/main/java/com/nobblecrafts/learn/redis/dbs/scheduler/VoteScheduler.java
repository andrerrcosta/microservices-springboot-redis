package com.nobblecrafts.learn.redis.dbs.scheduler;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.helper.VoteSchedulerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class VoteScheduler {
  private final VoteSchedulerHelper helper;

  public void schedule(AgendaDTO agenda) {
    var redisAgenda = helper.initializeRedisAgenda(agenda);
    helper.scheduleAgenda(redisAgenda);
  }

}
