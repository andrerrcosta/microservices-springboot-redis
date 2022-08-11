package com.nobblecrafts.learn.redis.dbs.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Vote {
  
  @NotNull
  private Long associateId;
  @NotNull
  private Long agendaId;
  @CPF
  private String associateCpf;
  @NotNull
  @NotEmpty
  private String vote;

}
