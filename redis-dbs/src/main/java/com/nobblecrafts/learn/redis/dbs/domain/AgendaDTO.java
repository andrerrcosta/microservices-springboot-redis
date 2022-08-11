package com.nobblecrafts.learn.redis.dbs.domain;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AgendaDTO {
    private Long id;
    private Date start;
    private Date end;
    private Set<Long> associates;
    private Map<Long, String> votes;
}
