package com.backend.controllers;


import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.User;
import com.backend.entities.User.ROLE;
import com.backend.service.UsersService;
import com.google.common.hash.Hashing;

@Controller
@RequestMapping("/user")
public class UserController {

	static Logger log = Logger.getLogger(UserController.class.getName());
	
	
	private UsersService userService;
	public UserController(UsersService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")	
	public @ResponseBody User login(@RequestBody Map<String, String> userData) {
		log.debug("Login: " +	userData.get("username") );
			return userService.login(userData.get("username"),(Hashing.sha256()
					  .hashString(userData.get("password"), StandardCharsets.UTF_8)
					  .toString()));
	}
	
	@PostMapping("/signup")
	public @ResponseBody String signup(@RequestParam String username, @RequestParam String name,
			@RequestParam String surname, @RequestParam String email, @RequestParam String password,
			@RequestParam String passwordRepeated) {
		
		if(username.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() ||password.isEmpty())
			return "BLANK_FIELDS";
		if(!password.equals(passwordRepeated))
			return "EQUAL_PASS";
		if(userService.findByUsername(username) != null)
			return "EXISTING_USERNAME";
		
		User u = new User();
		u.setUsername(username);
		u.setName(name);
		u.setSurname(surname);
		u.setEmail(email);
		u.setPassword(Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString());
		u.setPasswordRepeated(Hashing.sha256()
				  .hashString(passwordRepeated, StandardCharsets.UTF_8)
				  .toString());
		u.setRole(ROLE.USER);
		
		
		userService.save(u);
		return "success";
	}

}
