package com.backend.entities.terraform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.interfaces.IResource;

@Component
public class TerraformTemplate {

	private IProvider provider;
	private String processName;
	private Integer instanceNumber;
	private List<IResource> resources = new ArrayList<IResource>();
	private ProviderFactory factory;
	
	public TerraformTemplate(ProviderFactory factory) {
		this.factory = factory;
	}
	
	public void assignProvider(String providerName) {
		this.provider = factory.getProvider(providerName);
	}
	
	public String setRequiredProvider() {
		return provider.setRequiredProvider();
	}
	
	public String setProvider() {
		return provider.setProvider();
	}
	
	public String setResources() {
		 String result =resources.stream().map(e-> e.outputResource()).reduce("", String::concat);
		 return result;
	}


	public IProvider getProvider() {
		return provider;
	}


	public void setProvider(IProvider provider) {
		this.provider = provider;
	}


	public String getProcessName() {
		return processName;
	}


	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public Integer getInstanceNumber() {
		return instanceNumber;
	}


	public void setInstanceNumber(Integer instanceNumber) {
		this.instanceNumber = instanceNumber;
	}



}
