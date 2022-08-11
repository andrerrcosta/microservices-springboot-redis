package com.nobblecrafts.learn.redis.admin.service;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;

public interface SystemService {
    Agenda closeAgenda(AgendaDTO dto);
}
