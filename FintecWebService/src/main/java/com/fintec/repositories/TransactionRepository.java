package com.fintec.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fintec.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
