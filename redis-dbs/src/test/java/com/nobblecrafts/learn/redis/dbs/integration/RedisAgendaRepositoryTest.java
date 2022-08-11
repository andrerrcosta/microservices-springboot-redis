package com.nobblecrafts.learn.redis.dbs.integration;

import com.nobblecrafts.learn.redis.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.redis.dbs.mapper.AgendaMapper;
import com.nobblecrafts.learn.redis.dbs.util.AgendaSupplier;

import com.nobblecrafts.learn.redis.dbs.util.RedisVoteBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

/**
 * Não estou muito certo sobre como utilizar um embeddedredis para o mesmo
 * repositório. Isso tem que olhar
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedisAgendaRepositoryTest {

  @Autowired
  private RedisAgendaRepository repository;
  private AgendaSupplier supplier = new AgendaSupplier();
  private AgendaMapper mapper = AgendaMapper.INSTANCE;

  @Test
  @Transactional
  public void saveAgenda() {
    RedisVoteBuilder redisVoteBuilder = new RedisVoteBuilder();
    repository.deleteAll();
    var agenda = supplier.createAgenda(2L, 0L);
    log.info("\n\nAGENDA SUPPLIED {}\n\n", agenda);
    var redisAgenda = mapper.toRedisAgenda(agenda);
    log.info("\n\nAFTER MAP {}\n\n", redisAgenda);
    var saved = repository.save(redisAgenda);
    var findAll = repository.findAll();
    log.info("\n\nSAVED AGENDA {}\n\n", saved);
    log.info("\n\nALL AGENDAS {}\n\n", findAll);
    var recover = repository.findById(saved.getId());
    log.info("RECOVER {}", recover);
    Assertions.assertThat(recover.get()).isEqualTo(saved);
  }

}
