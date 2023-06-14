package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.ProviderFactory;
import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformDatabase.Provider;
import com.backend.repository.ProviderRepository;

@Service
public class ProviderService {

	ProviderRepository repository;
	RegionService regionService;
	ProviderFactory factory;
	
	public ProviderService(ProviderRepository providerRepository, RegionService regionService, ProviderFactory factory) {
		this.repository = providerRepository;
		this.regionService = regionService;
		this.factory = factory;
	}

	public List<Provider> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false)
	    .collect(Collectors.toList());
	}
	
	public void insert(Provider provider) {
		repository.save(provider);
	}
	
	public void delete(Provider provider) {
		IProvider providerCred = factory.getProvider(provider.getName());
		providerCred.deleteCredentials();
		regionService.findByProviderId(provider.getId()).forEach(region -> regionService.delete(region));
		repository.delete(provider);
	}

	public Provider findById(Integer id) {
		if (repository.findById(id).isEmpty())
			return null;
		return repository.findById(id).get();
	}

	public void update(Provider provider) {
		repository.save(provider);
	}

	public Provider findByName(String name) {
		return repository.findByName(name);
	}

}
