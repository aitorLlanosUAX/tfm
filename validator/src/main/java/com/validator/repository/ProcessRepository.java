package com.validator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.validator.entities.BatchProcess;
@Repository
public interface ProcessRepository extends CrudRepository<BatchProcess, Integer> {
}
