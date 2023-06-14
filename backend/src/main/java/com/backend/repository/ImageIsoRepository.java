package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.ImageIso;

public interface ImageIsoRepository extends CrudRepository<ImageIso, Integer> {
	
	@Query("Select i from ImageIso i where i.region_id = (?1) and i.provider_id = (?2)")
	List<ImageIso> findByRegionIdAndProviderId(Integer region_id, Integer provider_id);
	
	@Query("Select i from ImageIso i where LOWER(i.name) LIKE LOWER (?1)")
	List<ImageIso> search(String text);
}
