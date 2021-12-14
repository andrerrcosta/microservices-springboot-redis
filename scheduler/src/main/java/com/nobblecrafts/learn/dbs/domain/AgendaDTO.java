package com.nobblecrafts.learn.dbs.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {

  Long id;
  Date start;
  Date end;
  List<Long> associates;
  Map<Long, String> votes;
  
}
