package com.nobblecrafts.learn.redis.dbs.util;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.github.javafaker.Faker;
import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;

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
                .associates(LongStream.range(1, 20).boxed().collect(Collectors.toSet()))
                .id(id)
                .start(new Date(now + delay))
                .end(new Date(now + delay + 20000))
                .build();
    }

    public List<AgendaDTO> randomAgendas() {
        return null;
    }

    public Set<Vote> createRandomValidVotes(Set<Long> associates, Long agendaId) {
        var voto = faker.random().nextBoolean();
        return associates.stream()
                .map(id -> Vote.builder()
                        .agendaId(agendaId)
                        .associateId(id)
                        .associateCpf(cpfGenerator.cpf(false))
                        .vote(voto ? "sim" : "não")
                        .build())
                .collect(Collectors.toSet());
    }

    public List<Vote> createRandomInvalidVotes(List<Long> associates, Long agendaId) {
        var voto = faker.random().nextBoolean();
        return associates.stream()
                .map(id -> Vote.builder()
                        .agendaId(agendaId)
                        // .associateId(id)
                        .associateCpf(cpfGenerator.cpf(false))
                        .vote(voto ? "sim" : "não")
                        .build())
                .collect(Collectors.toList());
    }

}
