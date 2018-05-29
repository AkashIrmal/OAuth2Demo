package com.fintec.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fintec.model.TransactionLog;

public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {

}
