package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Getter
@Setter
public class AgendaDTO implements Serializable {

    String subject;
    String title;
    Long id;
    Date start;
    Date end;
    Set<Long> associates;
    Map<Long, String> votes;

}
