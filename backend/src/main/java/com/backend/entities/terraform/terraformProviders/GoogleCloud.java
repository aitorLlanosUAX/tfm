package com.backend.entities.terraform.terraformProviders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.entities.terraform.interfaces.IProvider;
import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.util.output.CredentialsOutput;

@Component
public class GoogleCloud implements IProvider {

	@Value(value = "${gcpDirectory}")
	private String credentialsDirectory;

	@Value(value = "${gcpSource}")
	private String source;
	
	@Value(value = "${gcpVersion}")
	private String version;
	private Region region;
	private ImageIso image;
	private Instance instance;

	private String creedentialRoute;
	private String projectId;

	@Override
	public String setRequiredProvider() {
		return "terraform{\n\trequired_providers{\n\t\tgoogle={\n\t\t\tsource=\"" + source + "\"\n\t\t\tversion=\""
				+ version + "\"\n\t\t}\n\t}\n}\n";
	}

	@Override
	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public String setProvider() {
		String creedentialsJson = credentialsDirectory + "/creedentials.json";
		this.projectId = getCredentials().get("projectId");
		return "provider \"google\"{\n\tcredentials = file(\""+ creedentialsJson +"\")\n\tproject=\""+projectId+"\"\n\tregion= \"" + region.getName() + "${count.index}\"\n\tzone=\""+region.getZone()+"\"\n}";
	}

	@Override
	public void setKeys(String... keys) {
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
	public String setInstanceResource(String instanceName, Integer instanceNumber) {
		return "resource \"google_compute_instance\" \"" + instanceName.toLowerCase() + "\"{\n\tcount=" + instanceNumber
				+ "\n\tname=\"" + instanceName.toLowerCase() + "\"\n\tmachine_type=\"" + instance.getName() + "\"\n\tzone=\""
				+ region.getZone() + "\"\n\tboot_disk {\n\t\t initialize_params {\n\t\t\timage = \"" + image.getName()
				+ "/" + image.getOperatingSystem()
				+ "\"\n\t\t}\n\t}\n\tnetwork_interface {\n\t\tnetwork = \"default\"\n\taccess_config {\n\t\t}\n\t}\n}";
	}

	@Override
	public String storeCredentials(Map<String, String> credentials) {
		this.creedentialRoute = credentials.get("Creedentials .json route");
		this.projectId = credentials.get("projectId");
		String toStore = "[default]\nprojectId=\"" + projectId + "\"";
		File creedentialsJson = new File(creedentialRoute);
		if (!creedentialsJson.exists())
			return null;
		String returnPath = new CredentialsOutput().createFile(credentialsDirectory, toStore);
		try {
			Files.move(Paths.get(creedentialRoute), Paths.get(credentialsDirectory + "/creedentials.json"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return returnPath;
	}

	@Override
	public void deleteCredentials() {
		new CredentialsOutput().deleteFile(credentialsDirectory + "/credentials");
		new CredentialsOutput().deleteFile(credentialsDirectory + "/creedentials.json");
		new CredentialsOutput().deleteFile(credentialsDirectory);
	}

	@Override
	public Map<String, String> getCredentials() {
		Map<String, String> map = new CredentialsOutput().getCredentials(credentialsDirectory + "/credentials");
		map.put("Creedentials .json route", credentialsDirectory + "/creedentials.json");
		return map;
	}

}
