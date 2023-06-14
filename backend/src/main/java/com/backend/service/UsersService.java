package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.User;
import com.backend.repository.UserRepository;

@Service
public class UsersService {

	@Autowired
	private UserRepository userRepository;

	public User login(String username, String password) {
		return userRepository.login(username, password);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}