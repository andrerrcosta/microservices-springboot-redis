package com.nobblecrafts.learn.dbs.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vote {
  
  @NotNull
  Long associateId;
  @NotNull
  Long agendaId;
  @CPF
  String associateCpf;
  @NotNull
  @NotEmpty
  String vote;

}
