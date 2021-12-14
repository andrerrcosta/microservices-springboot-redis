package com.nobblecrafts.learn.admin.service;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.admin.exception.BadRequestException;
import com.nobblecrafts.learn.admin.repository.AgendaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemService {

  private final AgendaRepository repository;

  @Transactional
  public Agenda closeAgenda(AgendaDTO dto) {
    var agenda = this.repository.findById(dto.getId())
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));

    //@formatter:off
    return this.repository.save(agenda
      .withVotes(dto.getVotes())
      .withIsClosed(true)
      .withIsOpen(false));
    //@formatter:on
  }

}
