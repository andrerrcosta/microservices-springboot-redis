package com.nobblecrafts.learn.dbs.integration;

import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class RedisAgendaRepositoryTest {

  private RedisAgendaRepository repository;
  private AgendaSupplier supplier;
  private AgendaMapper mapper;

  @Autowired
  public RedisAgendaRepositoryTest(ThreadPoolTaskScheduler taskScheduler) {
    // this.repository = repository;
    this.supplier = new AgendaSupplier();
    this.mapper = new AgendaMapper();
  }

  // @Test
  public void saveAgenda() {
    var agenda = supplier.createAgenda(2L, 0L);
    log.info("AGENDA SUPPLIED {}", agenda);
    var saved = repository.save(mapper.convertFromEntityToRedis(agenda));
    var recover = repository.findById(agenda.getId());
    log.info("RECOVER {}", recover);
  }

}
