package com.nobblecrafts.learn.redis.admin.jpa;

import java.util.HashSet;
import java.util.List;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.repository.AgendaRepository;
import com.nobblecrafts.learn.redis.admin.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@DisplayName("testando AgendaRepository")
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AgendaRepositoryTest {

  @Autowired
  private AgendaRepository repository;
  private AgendaSupplier supplier = new AgendaSupplier();

  @Test
  @Transactional
  public void A1_successfulAgendaSave() {

    Agenda agenda = supplier.createAgendaWithoutAssociates(null);

    var saved = repository.save(agenda.withAssociates(new HashSet<>()));
    Assertions.assertThat(saved).isNotNull();
    Assertions.assertThat(saved.getId()).isNotNull();
    Assertions.assertThat(saved.getTitle()).isEqualTo(agenda.getTitle());
  }

  @Test
  @Transactional
  public void A2_successfulAgendaUpdade() {
    Agenda agenda = supplier.createAgendaWithoutAssociates(null);

    var saved = repository.save(agenda);
    var updated = repository.save(saved.withTitle("Updated Title"));
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.getId()).isNotNull();
    Assertions.assertThat(updated.getTitle()).isEqualTo("Updated Title");
  }

  @Test
  @Transactional
  public void A3_AgendaNotFoundByTitle() {
    Agenda a1 = supplier.createAgendaWithoutAssociates(null);
    Agenda a2 = supplier.createAgendaWithoutAssociates(null);
    Agenda a3 = supplier.createAgendaWithoutAssociates(null);

    var saved = repository.saveAll(List.of(a1, a2, a3));
    var found = repository.findByTitle("tralala");

    Assertions.assertThat(found).isEmpty();
  }

}
