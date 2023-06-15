package com.backend.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.backend.entities.User;
import com.backend.entities.User.ROLE;
import com.backend.entities.terraform.terraformDatabase.Creedential;
import com.backend.entities.terraform.terraformDatabase.ProviderCLI;
import com.backend.repository.CreedentialRepository;
import com.backend.repository.ProviderCLIRepository;
import com.backend.repository.UserRepository;
import com.google.common.hash.Hashing;

@Service
public class InsertTerraformProvidersAvailablesData {

	@Autowired
	private ProviderCLIRepository providerRepository;

	@Autowired
	private CreedentialRepository creedentialsRepository;
	@Autowired
	private UserRepository userRepository;

	@Bean
	InitializingBean insertData() {
		return () -> {
			providerRepository.deleteAll();
			creedentialsRepository.deleteAll();

			ProviderCLI aws = new ProviderCLI();
			aws.setId(1);
			aws.setName("Aws");
			providerRepository.save(aws);
			Creedential aws_key = new Creedential();
			aws_key.setName("aws_key");
			aws_key.setProvider_id(providerRepository.findByName("Aws").getId());
			Creedential aws_secretKey = new Creedential();
			aws_secretKey.setName("aws_secretKey");
			aws_secretKey.setProvider_id(providerRepository.findByName("Aws").getId());
			
			ProviderCLI googleCloud = new ProviderCLI();
			googleCloud.setId(2);
			googleCloud.setName("Google Cloud");
			providerRepository.save(googleCloud);
			Creedential creedentials_route = new Creedential();
			creedentials_route.setName("Creedentials .json route");
			creedentials_route.setProvider_id(providerRepository.findByName("Google Cloud").getId());
			Creedential projectId = new Creedential();
			projectId.setName("projectId");
			projectId.setProvider_id(providerRepository.findByName("Google Cloud").getId());

			creedentialsRepository.save(creedentials_route);
			creedentialsRepository.save(projectId);
			
			creedentialsRepository.save(aws_key);
			creedentialsRepository.save(aws_secretKey);
			if (userRepository.findByUsername("admin") == null) {
				User admin = new User();
				admin.setName("Admin");
				admin.setUsername("admin");
				admin.setRole(ROLE.ADMIN);
				admin.setPassword(Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString());
				userRepository.save(admin);
			}
			
			if (userRepository.findByUsername("allanosi") == null) {
				User admin2 = new User();
				admin2.setName("Aitor");
				admin2.setSurname("Llanos");
				admin2.setUsername("allanosi");
				admin2.setRole(ROLE.ADMIN);
				admin2.setEmail("aitor@capgemini.com");
				admin2.setPassword(Hashing.sha256().hashString("cap", StandardCharsets.UTF_8).toString());
				userRepository.save(admin2);
			}
		};
	}
}
