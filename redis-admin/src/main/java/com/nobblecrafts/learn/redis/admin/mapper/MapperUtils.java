package com.nobblecrafts.learn.redis.admin.mapper;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.Associate;

import java.util.Set;
import java.util.stream.Collectors;

public class MapperUtils {

    public Associate longToAssociateId(Long id) {
        return  new Associate().withId(id);
    }

    public Agenda longToAgendaId(Long id) {
        return new Agenda().withId(id);
    }

    public Long associateIdToLong(Associate associate) {
        return associate.getId();
    }

    public Long agendaIdToLong(Agenda agenda) {
        return agenda.getId();
    }

    public Set<Associate> longToAssociateId(Set<Long> ids) {
        return ids.stream()
                .map(id -> new Associate().withId(id))
                .collect(Collectors.toSet());
    }

    public Set<Agenda> longToAgendaId(Set<Long> ids) {
        return ids.stream()
                .map(id -> new Agenda().withId(id))
                .collect(Collectors.toSet());
    }

    public Set<Long> associateIdToLong(Set<Associate> associates) {
        return associates.stream()
                .map(Associate::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> agendaIdToLong(Set<Agenda> agenda) {
        return agenda.stream()
                .map(Agenda::getId)
                .collect(Collectors.toSet());
    }
}
