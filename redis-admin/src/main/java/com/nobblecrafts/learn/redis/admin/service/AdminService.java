package com.nobblecrafts.learn.redis.admin.service;

import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;

import java.util.List;

public interface AdminService {
    AgendaDTO createNewAgenda(AgendaDTO dto);
    List<AgendaDTO> getAllAgendas();
    AgendaDTO getAgenda(Long id);
    AgendaDTO updateAgenda(AgendaDTO dto);
    AssociateDTO addAssociate(AssociateDTO dto);
    List<AssociateDTO> addAssociates(List<AssociateDTO> dtos);
    List<AssociateDTO> getAllAssociates();
    AssociateDTO getAssociate(Long id);
}
