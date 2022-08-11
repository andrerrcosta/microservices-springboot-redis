package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Getter
@Setter
public class AssociateDTO implements Serializable {

    Long id;
    String name;
    Set<Long> agendas;
    String cpf;

}
