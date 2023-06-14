package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.BatchProcess;

public interface ProcessRepository extends CrudRepository<BatchProcess, Integer> {

	@Query("Select p from BatchProcess p where LOWER(p.name) LIKE LOWER (?1) and p.deleted_at is null")
	List<BatchProcess> search(String text);
	
	@Query("Select p from BatchProcess p where p.deleted_at is null")
	List<BatchProcess> findAllNotDeleted();
	
	@Query("Select p from BatchProcess p where LOWER(p.name) LIKE LOWER (?1)")
	BatchProcess findByName(String processName);
	
}
