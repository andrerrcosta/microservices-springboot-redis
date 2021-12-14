package com.nobblecrafts.learn.admin.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.Associate;

public class AgendaSupplier {

  private Faker faker;

  public AgendaSupplier() {
    this.faker = new Faker();
  }

  public Agenda createAgenda(Date end) {

    return Agenda.builder()
      .associates(this.createSetOfAssociates(20))
      .subject(faker.funnyName().name())
      .title(faker.funnyName().name())
      .start(new Date())
      .end(end)
      .build();
  }

  public Agenda createAgendaWithoutAssociates(Date end) {

    return Agenda.builder()
      .subject(faker.funnyName().name())
      .title(faker.funnyName().name())
      .start(new Date())
      .end(end)
      .build();
  }

  public Set<Associate> createSetOfAssociates(int k) {
    GeradorDeCpf gc = new GeradorDeCpf();
    Set<Associate> associates = new HashSet<>();
    for(var i = 0; i < k; i++) {
      associates.add(Associate.builder()
        .cpf(gc.cpf(false))
        .id(Long.parseLong("" + k))
        .name(faker.name().fullName())
        .build());
    }
    return associates;
  }
  
}
