package com.backend.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.terraform.terraformDatabase.ProviderCLI;
import com.backend.service.ProviderCLIService;

@Controller
@RequestMapping("/providerCLI")
public class ProviderCLIController {

	ProviderCLIService providerCLIService;

	public ProviderCLIController(ProviderCLIService providerCLIService) {
		this.providerCLIService = providerCLIService;
	}

	@GetMapping("/list")
	public @ResponseBody List<ProviderCLI> listAll() {
		return providerCLIService.findAll();
	}


}
