package com.nobblecrafts.learn.admin.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class AssociateDTO {

  Long id;
  String name;
  List<Long> agendas;
  String cpf;

}
