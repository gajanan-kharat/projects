package com.javatechie.trx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatechie.trx.entity.Passanger;

@Repository
public interface PassangerRepository extends JpaRepository<Passanger, Long>  {

}
