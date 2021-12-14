package com.nobblecrafts.learn.admin.repository;

import java.util.List;

import com.nobblecrafts.learn.admin.domain.Agenda;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
  
  List<Agenda> findAll();

  List<Agenda> findByTitle(String title);

}
