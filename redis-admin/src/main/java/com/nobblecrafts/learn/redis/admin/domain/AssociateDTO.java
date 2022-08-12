package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Getter
@Setter
@ToString
public class AssociateDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;
    @Builder.Default
    private Set<Long> agendas = new HashSet<>();
    @NotNull
    @CPF
    private String cpf;

}
