package com.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.terraformDatabase.ProviderCLI;
import com.backend.repository.ProviderCLIRepository;

@Service
public class ProviderCLIService {

	ProviderCLIRepository providerCLIRepository;

	public ProviderCLIService(ProviderCLIRepository providerCLIRepository) {
		this.providerCLIRepository = providerCLIRepository;
	}

	public List<ProviderCLI> findAll() {
		return StreamSupport.stream(providerCLIRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	public ProviderCLI findById(Integer id) {
		Optional<ProviderCLI> provider = providerCLIRepository.findById(id);
		if(provider.isEmpty())
			return null;
		return provider.get();
	}
	
	public ProviderCLI findByName(String name) {
		return providerCLIRepository.findByName(name);
	}
	

}
