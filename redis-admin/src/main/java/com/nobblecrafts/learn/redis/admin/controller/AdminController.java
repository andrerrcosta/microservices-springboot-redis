package com.nobblecrafts.learn.redis.admin.controller;

import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;
import com.nobblecrafts.learn.redis.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping("criarpauta")
    public ResponseEntity<AgendaDTO> createAgenda(@RequestBody @Valid AgendaDTO agenda) {
        var output = service.createNewAgenda(agenda);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("agendas")
    public ResponseEntity<List<AgendaDTO>> getAllAgendas() {
        var agendas = service.getAllAgendas();
        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    @GetMapping("agendas/{id}")
    public ResponseEntity<AgendaDTO> getAllAgendas(@PathParam("id") Long id) {
        var agenda = service.getAgenda(id);
        return new ResponseEntity<>(agenda, HttpStatus.OK);
    }

    @PostMapping("addassociado")
    public ResponseEntity<AssociateDTO> createAssociate(@RequestBody @Valid AssociateDTO associate) {
        var output = service.addAssociate(associate);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping("addassociados")
    public ResponseEntity<List<AssociateDTO>> createAssociates(@RequestBody List<AssociateDTO> associates) {
        var output = service.addAssociates(associates);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("associados")
    public ResponseEntity<List<AssociateDTO>> getAllAssociates() {
        var output = service.getAllAssociates();
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("associados/{id}")
    public ResponseEntity<AssociateDTO> getAllAssociates(@PathParam("id") Long id) {
        var output = service.getAssociate(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
