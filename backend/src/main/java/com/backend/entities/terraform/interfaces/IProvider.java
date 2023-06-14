package com.backend.entities.terraform.interfaces;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.entities.terraform.terraformDatabase.Region;
@Component
public interface IProvider {

	public String setRequiredProvider();

	public void setRegion(Region region);

	public String setProvider();

	public void setKeys(String... keys);

	public void setImage(ImageIso iso);

	public void setInstance(Instance instance);

	public String setInstanceResource(String instanceName, Integer instanceNumber);

	public String storeCredentials(Map<String, String> credentials);

	public void deleteCredentials();
	
	public Map<String, String> getCredentials();


}
