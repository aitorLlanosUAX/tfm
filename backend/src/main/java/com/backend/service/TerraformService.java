package com.backend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.entities.terraform.ProviderFactory;
import com.backend.entities.terraform.TerraformTemplate;
import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.entities.terraform.terraformDatabase.Provider;
import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.util.output.TerraformOutput;

@Service
public class TerraformService {

	@Value(value = "${terraformPath}")
	private String pathTerraform;

	ProviderService providerService;

	RegionService regionService;

	ImageIsoService imageService;

	InstanceService instanceService;

	ProviderFactory factory;
	
	TerraformTemplate template;

	public TerraformService(ProviderService providerService, RegionService regionService, ImageIsoService imageService,
			InstanceService instanceService, ProviderFactory factory, TerraformTemplate template) {
		this.providerService = providerService;
		this.regionService = regionService;
		this.imageService = imageService;
		this.instanceService = instanceService;
		this.factory = factory;
		this.template = template;
	}

	public String createTemplate(Map<String, String> templateData) {

		Provider provider = providerService.findById(Integer.parseInt(templateData.get("provider_id")));
		if (provider == null) {
			return null;
		}

		template.setProcessName( templateData.get("processName"));
		template.assignProvider(provider.getName());
		template.setInstanceNumber(Integer.valueOf(templateData.get("instanceNumber")));

		IProvider templateProvider = template.getProvider();
		Region region = regionService.findById(Integer.parseInt(templateData.get("region_id")));
		if (region == null) {
			return null;
		}

		templateProvider.setRegion(region);

		ImageIso image = imageService.findById(Integer.parseInt(templateData.get("image_id")));
		if (image == null) {
			return null;
		}

		templateProvider.setImage(image);

		Instance instance = instanceService.findById(Integer.parseInt(templateData.get("instance_id")));
		if (instance == null) {
			return null;
		}

		templateProvider.setInstance(instance);
		TerraformOutput output = new TerraformOutput(pathTerraform);

		return output.createFile(template);
	}

}
