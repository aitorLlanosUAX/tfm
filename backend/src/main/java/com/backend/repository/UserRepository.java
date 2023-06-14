package com.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("Select u from User u where LOWER(u.username) LIKE LOWER (?1)")
	User findByUsername(String username);
	
	@Query("Select u from User u where LOWER(u.username) LIKE LOWER (?1) and LOWER(u.password) LIKE LOWER(?2)")
	User login(String username, String password);
	
	
}
