package com.nobblecrafts.learn.admin.util;

import java.util.stream.Collectors;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.admin.domain.Associate;

public class AgendaMapper extends ModelMapper<AgendaDTO, Agenda> {

  public AgendaMapper() {
    super(AgendaMapper::convertToAgenda, AgendaMapper::convertToDTO);
  }

  private static AgendaDTO convertToDTO(Agenda agenda) {
    //@formatter:off
    return AgendaDTO.builder()
        .associates(agenda.getAssociates()
          .stream()
          .map(Associate::getId).collect(Collectors.toList()))
        .id(agenda.getId())
        .start(agenda.getStart())
        .end(agenda.getEnd())
        .votes(agenda.getVotes())
        .build();
    //@formatter:on
  }

  private static Agenda convertToAgenda(AgendaDTO dto) {
    return null;
  }

}