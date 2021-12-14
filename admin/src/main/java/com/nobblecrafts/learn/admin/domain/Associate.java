package com.nobblecrafts.learn.admin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@Entity
@Table(name = "Associate")
@NoArgsConstructor
@AllArgsConstructor
@With
public class Associate implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  //@formatter:off
  @Builder.Default
  @ManyToMany(cascade =  CascadeType.MERGE)
  Set<Agenda> agendas = new HashSet<>();
  //@formatter:on

  @CPF
  @Column(name = "cpf", nullable = false)
  String cpf;

}
