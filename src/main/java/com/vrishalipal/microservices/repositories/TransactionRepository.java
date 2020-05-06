package com.vrishalipal.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vrishalipal.microservices.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
