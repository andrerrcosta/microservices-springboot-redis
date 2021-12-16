package com.nobblecrafts.learn.dbs.controller;

import javax.validation.Valid;

import com.nobblecrafts.learn.dbs.domain.Vote;
import com.nobblecrafts.learn.dbs.service.VotationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "client")
@RequiredArgsConstructor
public class AssociateController {

  private final VotationService service;

  @PostMapping(path = "vote")
  public ResponseEntity<Vote> vote(@RequestBody @Valid Vote vote) {
    service.vote(vote);
    return new ResponseEntity<>(vote, HttpStatus.OK);
  }

}
