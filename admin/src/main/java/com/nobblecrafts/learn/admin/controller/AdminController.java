package com.nobblecrafts.learn.admin.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.nobblecrafts.learn.admin.domain.Agenda;
import com.nobblecrafts.learn.admin.domain.Associate;
import com.nobblecrafts.learn.admin.service.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService service;

  @PostMapping("criarpauta")
  public ResponseEntity<Agenda> createAgenda(@RequestBody @Valid Agenda agenda) {
    var output = service.createNewAgenda(agenda);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @GetMapping("agendas")
  public ResponseEntity<List<Agenda>> getAllAgendas() {
    var agendas = service.getAllAgendas();
    return new ResponseEntity<>(agendas, HttpStatus.OK);
  }

  @GetMapping("agendas/{id}")
  public ResponseEntity<Agenda> getAllAgendas(@PathParam("id") Long id) {
    var agenda = service.getAgenda(id);
    return new ResponseEntity<>(agenda, HttpStatus.OK);
  }

  @PostMapping("addassociado")
  public ResponseEntity<Associate> createAssociate(@RequestBody @Valid Associate associate) {
    var output = service.addAssociate(associate);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @PostMapping("addassociados")
  public ResponseEntity<List<Associate>> createAssociates(@RequestBody List<Associate> associates) {
    var output = service.addAssociates(associates);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @GetMapping("associados")
  public ResponseEntity<List<Associate>> getAllAssociates() {
    var output = service.getAllAssociates();
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @GetMapping("associados/{id}")
  public ResponseEntity<Associate> getAllAssociates(@PathParam("id") Long id) {
    var output = service.getAssociate(id);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

}
