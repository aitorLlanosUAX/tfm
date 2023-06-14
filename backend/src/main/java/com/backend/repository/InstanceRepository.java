package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.terraform.terraformDatabase.Instance;

public interface InstanceRepository extends CrudRepository<Instance, Integer> {
	
	@Query("Select i from Instance i where i.imagen_id = (?1)")
	List<Instance> findByImageId(Integer image_id);
	
	@Query("Select i from Instance i where LOWER(i.name) LIKE LOWER (?1)")
	List<Instance> search(String text);

}
