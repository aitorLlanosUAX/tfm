package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
	@Query("Select p from Provider p where LOWER(p.name) LIKE LOWER (?1)")
	List<Provider> search(String text);
	
	@Query("Select p from Provider p where LOWER(p.name) LIKE LOWER (?1)")
	Provider findByName(String username);

}
