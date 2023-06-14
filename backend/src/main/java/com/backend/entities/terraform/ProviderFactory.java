package com.backend.entities.terraform;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformProviders.Aws;
import com.backend.entities.terraform.terraformProviders.GoogleCloud;

@Service
public class ProviderFactory {

	private Aws aws;

	private GoogleCloud gcp;

	public ProviderFactory(Aws aws, GoogleCloud gcp) {
		this.aws = aws;
		this.gcp = gcp;
	}

	public IProvider getProvider(String name) {
		if (name.toLowerCase().equals("aws")) {
			return aws;
		}
		if (name.toLowerCase().equals("google cloud")) {
			return gcp;
		}
		return null;
	}

}
