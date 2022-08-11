package com.nobblecrafts.learn.redis.admin.service.impl;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.exception.BadRequestException;
import com.nobblecrafts.learn.redis.admin.repository.AgendaRepository;

import com.nobblecrafts.learn.redis.admin.service.SystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemServiceImpl implements SystemService {

  private final AgendaRepository repository;

  @Override
  @Transactional
  public Agenda closeAgenda(AgendaDTO dto) {
    var agenda = this.repository.findById(dto.getId())
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));

    return this.repository.save(agenda
      .withVotes(dto.getVotes())
      .withIsClosed(true)
      .withIsOpen(false));
  }

}
