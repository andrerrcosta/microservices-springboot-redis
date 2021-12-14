package com.nobblecrafts.learn.admin.service;

import java.util.List;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.Associate;
import com.nobblecrafts.learn.admin.exception.BadRequestException;
import com.nobblecrafts.learn.admin.repository.AgendaRepository;
import com.nobblecrafts.learn.admin.repository.AssociateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {

  private final AgendaRepository repository;
  private final AssociateRepository associateRepository;
  private final AgendaPublisher publisher;

  @Autowired
  public AdminService(AgendaRepository repository, AssociateRepository associateRepository, AgendaPublisher publisher) {
    this.repository = repository;
    this.associateRepository = associateRepository;
    this.publisher = publisher;
  }

  @Transactional
  public Agenda createNewAgenda(Agenda agenda) {
    var model = this.repository.save(agenda);
    log.info("Model {}", model);
    if (model != null)
      publisher.publishAgenda(agenda);
    return model;
  }

  @Transactional
  public List<Agenda> getAllAgendas() {
    return repository.findAll();
  }

  @Transactional
  public Agenda getAgenda(Long id) {
    return this.repository.findById(id)
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));
  }

  @Transactional
  public Agenda updateAgenda(Agenda agenda) {
    var model = this.repository.findById(agenda.getId())
        .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));
    return this.repository.save(agenda.withId(model.getId()));
  }

  @Transactional
  public Associate addAssociate(Associate associate) {
    return associateRepository.save(associate);
  }

  @Transactional
  public List<Associate> addAssociates(List<Associate> associates) {
    return associateRepository.saveAll(associates);
  }

  @Transactional
  public List<Associate> getAllAssociates() {
    return associateRepository.findAll();
  }

  @Transactional
  public Associate getAssociate(Long id) {
    return associateRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Associado não encontrado"));
  }

}
