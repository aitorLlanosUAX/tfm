package com.backend.controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.terraform.terraformDatabase.Provider;
import com.backend.service.ProviderService;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	ProviderService providerService;

	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}

	@PostMapping("/delete")
	public @ResponseBody int delete(@RequestParam Integer id) {
		Provider provider = providerService.findById(id);
		if (provider == null)
			return Response.SC_NOT_FOUND;
		providerService.delete(provider);
		return Response.SC_OK;
	}

	@GetMapping("/list")
	public @ResponseBody List<Provider> listAll() {
		return providerService.findAll();
	}

	@PostMapping("/update")
	public @ResponseBody int update(@RequestBody Map<String, String> regionData) {
		Provider provider = providerService.findById(Integer.valueOf(regionData.get("id")));
		if (provider == null)
			return Response.SC_NOT_FOUND;
		provider.setName(regionData.get("name"));

		providerService.update(provider);
		return Response.SC_OK;
	}

	@GetMapping("/findById")
	public @ResponseBody Provider findById(@RequestParam Integer id) {
		if (providerService.findById(id) == null)
			return null;
		return providerService.findById(id);
	}

}
