package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "associates")
@NoArgsConstructor
@AllArgsConstructor
@With
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    Set<Agenda> agendas = new HashSet<>();

    @CPF
    @Column(name = "cpf", nullable = false)
    String cpf;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Associate that = (Associate) obj;
        return id.equals(that.id);
    }

}
