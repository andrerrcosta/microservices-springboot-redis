package com.nobblecrafts.learn.redis.admin.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javafaker.Faker;
import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.domain.Associate;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;

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
      .startVotation(new Date())
      .endVotation(end)
      .build();
  }

  public Agenda createAgendaWithoutAssociates(Date end) {

    return Agenda.builder()
      .subject(faker.funnyName().name())
      .title(faker.funnyName().name())
      .startVotation(new Date())
      .endVotation(end)
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

  public AgendaDTO createAgendaDTO(Date end) {

    return AgendaDTO.builder()
            .associates(this.createSetOfAssociatesDTO(20).stream().map(AssociateDTO::getId).collect(Collectors.toSet()))
            .subject(faker.funnyName().name())
            .title(faker.funnyName().name())
            .startVotation(new Date())
            .endVotation(end)
            .build();
  }

  public AgendaDTO createAgendaWithoutAssociatesDTO(Date end) {

    return AgendaDTO.builder()
            .subject(faker.funnyName().name())
            .title(faker.funnyName().name())
            .startVotation(new Date())
            .endVotation(end)
            .build();
  }

  public Set<AssociateDTO> createSetOfAssociatesDTO(int k) {
    GeradorDeCpf gc = new GeradorDeCpf();
    Set<AssociateDTO> associates = new HashSet<>();
    for(var i = 0; i < k; i++) {
      associates.add(AssociateDTO.builder()
              .cpf(gc.cpf(false))
              .id(Long.parseLong("" + k))
              .name(faker.name().fullName())
              .build());
    }
    return associates;
  }
  
}
