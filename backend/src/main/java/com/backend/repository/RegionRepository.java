package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.Region;

public interface RegionRepository extends CrudRepository<Region, Integer> {
	
	@Query("Select r from Region r where r.provider_id = (?1)")
	List<Region> findByProviderId(Integer provider_id);
	
	@Query("Select r from Region r where LOWER(r.name) LIKE LOWER (?1)")
	List<Region> search(String text);

}
