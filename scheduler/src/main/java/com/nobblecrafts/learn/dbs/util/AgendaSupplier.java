package com.nobblecrafts.learn.dbs.util;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.github.javafaker.Faker;
import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.domain.Vote;

public class AgendaSupplier {

  private final Faker faker;
  private final GeradorDeCpf cpfGenerator;

  public AgendaSupplier() {
    faker = new Faker(new Locale("pt-BR"));
    cpfGenerator = new GeradorDeCpf();
  }

  public AgendaDTO createAgenda(Long id, Long delay) {
    var now = new Date().getTime();
    return AgendaDTO.builder()
        .associates(LongStream.range(1, 20).boxed().collect(Collectors.toList()))
        .id(id)
        .start(new Date(now + delay))
        .build();
  }

  public List<AgendaDTO> randomAgendas() {
    return null;
  }

  public List<Vote> createRandomVotes(List<Long> associates, Long agendaId) {
    return associates.stream()
        .map(id -> Vote.builder()
            .agendaId(agendaId)
            .associateId(id)
            .associateCpf(cpfGenerator.cpf(false))
            .vote(faker.random().nextBoolean() ? "sim" : "não")
            .build())
        .collect(Collectors.toList());
  }

}
