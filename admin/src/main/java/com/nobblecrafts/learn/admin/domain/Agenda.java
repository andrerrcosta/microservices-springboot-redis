package com.nobblecrafts.learn.admin.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@Data
@Builder
@With
@Table(name = "Agenda")
@NoArgsConstructor
@AllArgsConstructor
public class Agenda implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String subject;
  String title;

  @Column(name = "start", nullable = false)
  Date start;

  Date end;

  //@formatter:off
  @Builder.Default
  @ManyToMany(cascade =  CascadeType.MERGE)
  @JoinTable(
    name = "agenda_associate", 
    joinColumns = { @JoinColumn(name = "agenda_id") }, 
    inverseJoinColumns = {
      @JoinColumn(name = "associate_id") }
  )
  Set<Associate> associates = new HashSet<>();
  //@formatter:on

  @Builder.Default
  @ElementCollection
  Map<Long, String> votes = new HashMap<>();

  @Builder.Default
  Boolean isClosed = false;

  @Builder.Default
  Boolean isOpen = false;

}
