package com.nobblecrafts.learn.admin.util;

import java.util.stream.Collectors;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.Associate;
import com.nobblecrafts.learn.admin.domain.AssociateDTO;

public class AssociateMapper extends ModelMapper<AssociateDTO, Associate> {

  public AssociateMapper() {
    super(AssociateMapper::convertToAssociate, AssociateMapper::convertToDTO);
  }

  private static AssociateDTO convertToDTO(Associate associate) {
    
    //@formatter:off
    return AssociateDTO.builder()
        .agendas(associate.getAgendas()
          .stream()
          .map(Agenda::getId)
          .collect(Collectors.toList()))
        .id(associate.getId())
        .cpf(associate.getCpf())
        .name(associate.getName())
        .build();
    //@formatter:on
  }

  private static Associate convertToAssociate(AssociateDTO dto) {
    return null;
  }
}
