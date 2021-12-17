package com.nobblecrafts.learn.dbs.integration;

import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;
import com.nobblecrafts.learn.dbs.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Não estou muito certo sobre como utilizar um embeddedredis para o mesmo
 * repositório. Isso tem que olhar
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedisAgendaRepositoryTest {

  private RedisAgendaRepository repository;
  private AgendaSupplier supplier;
  private AgendaMapper mapper;

  @Autowired
  public RedisAgendaRepositoryTest(RedisAgendaRepository repository) {
    this.repository = repository;
    this.supplier = new AgendaSupplier();
    this.mapper = new AgendaMapper();
  }

  @Test
  public void saveAgenda() {
    var agenda = supplier.createAgenda(2L, 0L);
    log.info("AGENDA SUPPLIED {}", agenda);
    var saved = repository.save(mapper.convertFromDTOToEntity(agenda));
    var recover = repository.findById(agenda.getId());
    log.info("RECOVER {}", recover);
    Assertions.assertThat(recover.get()).isEqualTo(saved);
  }

}
