package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Getter
@Setter
@ToString
public class AgendaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -79503534916015371L;
    Long id;
    @NotNull
    String subject;
    @NotNull
    String title;
    @NotNull
    Date startVotation;
    Date endVotation;
    @Builder.Default
    Set<Long> associates = new HashSet<>();
    @Builder.Default
    Map<Long, String> votes = new HashMap<>();

}
