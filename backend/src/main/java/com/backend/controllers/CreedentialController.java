package com.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.terraform.terraformDatabase.Creedential;
import com.backend.service.CreedentialService;

@Controller
@RequestMapping("/creedentials")
public class CreedentialController {

	CreedentialService creedentialService;

	public CreedentialController(CreedentialService creedentialService) {
		this.creedentialService = creedentialService;
	}

	@GetMapping("/listFromProvider")
	public @ResponseBody List<Creedential> listAll(@RequestParam Integer id) {
		return creedentialService.findAllByProvider(id);
	}
	
	@GetMapping("/listFromProviderName")
	public @ResponseBody List<Creedential> findAllByProviderName(@RequestParam String name ) {
		return creedentialService.findAllByProviderName(name);
	}


	@PostMapping("/add")
	public @ResponseBody String insert(@RequestBody Map<String, String> processDate) {
		return creedentialService.addCredentials(processDate);
	}
	
	@GetMapping("/getCredentialsByProviderName")
	public @ResponseBody Map<String, String> getCredentials(@RequestParam String name ){
		return creedentialService.getCredentials(name);
	}
	
	@PostMapping("/update")
	public @ResponseBody String update(@RequestBody Map<String, String> processDate) {
		creedentialService.updateCredentials(processDate);
		return "Success";
	}

}
