package com.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.ProviderFactory;
import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformDatabase.Creedential;
import com.backend.entities.terraform.terraformDatabase.Provider;
import com.backend.entities.terraform.terraformDatabase.ProviderCLI;
import com.backend.repository.CreedentialRepository;

@Service
public class CreedentialService {

	CreedentialRepository creedentialRepository;
	ProviderCLIService providerCLIService;
	ProviderService providerService;
	ProviderFactory factory;

	public CreedentialService(CreedentialRepository creedentialRepository, ProviderCLIService providerCLIService,
			ProviderService providerService,ProviderFactory factory) {
		this.creedentialRepository = creedentialRepository;
		this.providerCLIService = providerCLIService;
		this.providerService = providerService;
		this.factory = factory;
	}

	public List<Creedential> findAllByProvider(Integer id) {
		return creedentialRepository.findAllFromProvider(id);
	}

	public List<Creedential> findAllByProviderName(String name) {
		ProviderCLI provider = providerCLIService.findByName(name);
		return creedentialRepository.findAllFromProvider(provider.getId());
	}

	public String addCredentials(Map<String, String> processDate) {
		Provider newProvider = new Provider();
		newProvider.setName(providerCLIService.findById(Integer.valueOf(processDate.get("provider_id"))).getName());
		if (providerService.findByName(newProvider.getName()) != null)
			return "EXISTING_PROVIDER";
		providerService.insert(newProvider);
		IProvider provider = factory.getProvider(newProvider.getName());
		newProvider.setPathCredentials(provider.storeCredentials(processDate));
		providerService.update(newProvider);
		return "Success";
	}

	public void updateCredentials(Map<String, String> processDate) {
		Provider newProvider = new Provider();
		newProvider.setName(providerCLIService.findByName(processDate.get("name")).getName());
		IProvider provider = factory.getProvider(newProvider.getName());
		provider.deleteCredentials();
		newProvider.setPathCredentials(provider.storeCredentials(processDate));
	}

	public Map<String, String> getCredentials(String name) {
		IProvider providerCred = factory.getProvider(name);
		return providerCred.getCredentials();
	}

}
