package com.backend.service;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.BatchProcess;
import com.backend.repository.ProcessRepository;

@Service
public class ProcessService {

	ProcessRepository processRepository;

	TerraformService terraformService;

	public ProcessService(ProcessRepository processRepository, TerraformService terraformService) {
		this.processRepository = processRepository;
		this.terraformService = terraformService;
	}

	public void insert(BatchProcess process) {
		processRepository.save(process);
	}

	public void deleteById(Integer process_id) {
		processRepository.deleteById(process_id);
	}

	
	public List<BatchProcess> findAll() {
		return StreamSupport.stream(processRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	public List<BatchProcess> findAllNotDeleted() {
		return processRepository.findAllNotDeleted();
	}

	public String checkRequiredInsertParams(Map<String, String> processData) {
		if (processData.get("processName") == null)
			return "Process name not found";
		if (processData.get("provider_id") == null)
			return "Provider not found";
		if (Integer.valueOf(processData.get("instanceNumber")) <= 0)
			return "Number of instances cannot be negative";
		if (processData.get("region_id") == null)
			return "Region not found";
		if (processData.get("image_id") == null)
			return "Image not found";
		if (processData.get("instance_id") == null)
			return "Instance not found";
		if(processRepository.findByName(processData.get("processName")) != null)
			return "There is a process with that name";
		return null;
	}

	public String insertNewProcess(Map<String, String> processData) {
		BatchProcess processToInsert = new BatchProcess();
		processToInsert.setName(processData.get("processName"));
		if(processData.get("description").equals("undefined"))
			processToInsert.setDescription(processData.get(""));
		processToInsert.setDescription(processData.get("description"));
		processToInsert.setCreated_at(new Date(System.currentTimeMillis()));
		processToInsert.setStatus("Pending");
		processToInsert.setActive(true);
		String terraformTemplatePath = terraformService.createTemplate(processData);
		if(terraformTemplatePath == null)
			return null;
		processToInsert.setPathTemplate(terraformTemplatePath);
		insert(processToInsert);
		return "Success";

	}
	
	public void update(BatchProcess process) {
		processRepository.save(process);

	}

	public List<BatchProcess> findByPartialText(String text) {
		String toSearch = "%" + text + "%";
		return processRepository.search(toSearch);
	}
	
	public BatchProcess findById(Integer id) {
		if (processRepository.findById(id).isEmpty())
			return null;
		return processRepository.findById(id).get();
	}

	public File getTemplate(Integer process_id) {
		Optional<BatchProcess> process = processRepository.findById(process_id);
		if (process.isEmpty())
			return null;
		return new File(process.get().getPathTemplate());
	}

}