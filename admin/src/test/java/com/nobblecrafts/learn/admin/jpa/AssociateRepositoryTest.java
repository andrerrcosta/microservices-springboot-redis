package com.nobblecrafts.learn.admin.jpa;

import java.util.ArrayList;

import com.nobblecrafts.learn.admin.repository.AssociateRepository;
import com.nobblecrafts.learn.admin.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("testando AssociateRepository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AssociateRepositoryTest {

  @Autowired
  private AssociateRepository repository;
  private AgendaSupplier supplier = new AgendaSupplier();

  @Test
  public void successfulAssociateSave() {

    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);

    var saved = repository.save(associate);
    Assertions.assertThat(saved).isNotNull();
    Assertions.assertThat(saved.getId()).isNotNull();
    Assertions.assertThat(saved.getName()).isEqualTo(associate.getName());
  }

  @Test
  public void successfulAssociateUpdade() {
    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);

    var saved = repository.save(associate);
    var updated = repository.save(associate.withName("Updated Name"));
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.getId()).isNotNull();
    Assertions.assertThat(updated.getName()).isEqualTo("Updated Name");
  }

  @Test
  public void associateNotFoundByName() {
    var associates = new ArrayList<>(supplier.createSetOfAssociates(3));

    var saved = repository.saveAll(associates);
    var found = repository.findByName("tralala");

    Assertions.assertThat(found).isEmpty();
  }

  @Test
  public void saveThrowsConstraintViolationException() {
    var associate = new ArrayList<>(supplier.createSetOfAssociates(1)).get(0);
    Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
        .isThrownBy(() -> repository.save(associate.withCpf(null)));
  }

}
