package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.Creedential;

public interface CreedentialRepository extends CrudRepository<Creedential, Integer> {

	
	@Query("Select c from Creedential c where c.provider_id = (?1)")
	List<Creedential> findAllFromProvider(Integer id);
}
