package com.nobblecrafts.learn.dbs.util;

import java.util.ArrayList;
import java.util.HashSet;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.domain.RedisAgenda;

public class AgendaMapper extends ModelMapper<RedisAgenda, AgendaDTO> {

  public AgendaMapper() {
    super(AgendaMapper::convertToAgendaDTO, AgendaMapper::convertToRedisAgenda);
  }

  private static RedisAgenda convertToRedisAgenda(AgendaDTO agenda) {
    return RedisAgenda.builder()
      .associates(new HashSet<>(agenda.getAssociates()))
      .id(agenda.getId())
      .start(agenda.getStart())
      .end(agenda.getEnd())
      .votes(agenda.getVotes())
      .build();
  }

  private static AgendaDTO convertToAgendaDTO(RedisAgenda agenda) {
    return AgendaDTO.builder()
    .associates(new ArrayList<>(agenda.getAssociates()))
    .id(agenda.getId())
    .start(agenda.getStart())
    .votes(agenda.getVotes())
    .end(agenda.getEnd())
    .build();
  }

}