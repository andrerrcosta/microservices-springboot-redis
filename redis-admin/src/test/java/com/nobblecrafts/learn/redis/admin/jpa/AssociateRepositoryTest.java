package com.nobblecrafts.learn.redis.admin.jpa;

import java.util.ArrayList;

import com.nobblecrafts.learn.redis.admin.repository.AssociateRepository;
import com.nobblecrafts.learn.redis.admin.util.AgendaSupplier;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayName("testando AssociateRepository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AssociateRepositoryTest {

  @Autowired
  private AssociateRepository repository;
  private AgendaSupplier supplier = new AgendaSupplier();

  @Test
  @Transactional
  public void successfulAssociateSave() {

    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);

    var saved = repository.save(associate);
    Assertions.assertThat(saved).isNotNull();
    Assertions.assertThat(saved.getId()).isNotNull();
    Assertions.assertThat(saved.getName()).isEqualTo(associate.getName());
  }

  @Test
  @Transactional
  public void successfulAssociateUpdade() {
    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);
    log.info("\n\nALL ASSOCIATES: {}\n\n", repository.findAll());
    log.info("\n\nAssociate: {}\n\n", associate);
    var saved = repository.save(associate);
    saved.setName("Updated Name");
    var updated = repository.save(saved);
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.getId()).isNotNull();
    Assertions.assertThat(updated.getName()).isEqualTo("Updated Name");
  }

  @Test
  @Transactional
  public void associateNotFoundByName() {
    var associates = new ArrayList<>(supplier.createSetOfAssociates(3));

    var saved = repository.saveAll(associates);
    var found = repository.findByName("tralala");

    Assertions.assertThat(found).isEmpty();
  }

  
  @Test
  @Transactional
  public void saveThrowsConstraintViolationException() {
    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);
    Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
        .isThrownBy(() -> repository.save(associate.withCpf(null)));
  }

}
