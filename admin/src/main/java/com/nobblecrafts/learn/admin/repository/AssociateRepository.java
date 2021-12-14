package com.nobblecrafts.learn.admin.repository;

import java.util.List;

import com.nobblecrafts.learn.admin.domain.Associate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

  List<Associate> findByName(String name);
  
}
