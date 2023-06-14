package com.executor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.executor.entities.BatchProcess;

@Repository
public interface ProcessRepository extends CrudRepository<BatchProcess, Integer> {

}
