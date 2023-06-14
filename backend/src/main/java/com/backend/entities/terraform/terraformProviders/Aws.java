package com.backend.entities.terraform.terraformProviders;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.util.output.CredentialsOutput;

@Component
public class Aws implements IProvider {

	@Value(value = "${awsDirectory}")
	private String credentialsDirectory;
	
	@Value(value = "${awsSource}")
	private String source = "hashicorp/aws";
	
	@Value(value = "${awsVersion}")
	private String version;
	private Region region;
	private ImageIso image;
	private Instance instance;

	private String access_key;
	private String secret_key;

	public Aws() {	}

	@Override
	public void setKeys(String... keys) {
		this.access_key = keys[0];
		this.secret_key = keys[1];
	}

	@Override
	public String setRequiredProvider() {
		return "terraform{\n\trequired_providers{\n\t\taws={\n\t\t\tsource=\"" + source + "\"\n\t\t\tversion=\""
				+ version + "\"\n\t\t}\n\t}\n}\n";
	}

	@Override
	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public String setProvider() {
		if (access_key == null || secret_key == null)
			return "provider \"aws\"{\n\tregion= \"" + region.getName() + "\"\n}";
		return "provider \"aws\"{\n\tregion= \"" + region.getName() + "\"\n\taccess_key=\"" + access_key
				+ "\"\n\tsecret_key=\"" + secret_key + "\"\n}\n";
	}

	public String setInstanceResource(String instanceName, Integer instanceNumber) {
		return "resource \"aws_instance\" \"" + instanceName + "\"{\n\tcount=" + instanceNumber + "\n\tami=\""
				+ image.getName() + "\"\n\tinstance_type=\"" + instance.getName() + "\"\n}";
	}

	@Override
	public void setImage(ImageIso iso) {
		this.image = iso;
	}

	@Override
	public void setInstance(Instance instance) {
		this.instance = instance;

	}

	@Override
	public String storeCredentials(Map<String, String> credentials) {
		this.access_key = credentials.get("aws_key");
		this.secret_key = credentials.get("aws_secretKey");
		String toStore = "[default]\naws_access_key_id=\"" + access_key + "\"\naws_secret_access_key=\"" + secret_key
				+ "\"";
		return new CredentialsOutput().createFile(credentialsDirectory, toStore);
	}

	@Override
	public void deleteCredentials() {
		new CredentialsOutput().deleteFile(credentialsDirectory + "/credentials");
		new CredentialsOutput().deleteFile(credentialsDirectory);
	}

	public Map<String, String> getCredentials() {
		return new CredentialsOutput().getCredentials(credentialsDirectory + "/credentials");
	}

}
