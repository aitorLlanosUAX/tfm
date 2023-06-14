package com.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.ProviderCLI;

public interface ProviderCLIRepository extends CrudRepository<ProviderCLI, Integer> {
	@Query("Select p from ProviderCLI p where LOWER(p.name) LIKE LOWER (?1)")
	ProviderCLI findByName(String username);
}
