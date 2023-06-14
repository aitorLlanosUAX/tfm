package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.repository.InstanceRepository;

@Service
public class InstanceService {

	InstanceRepository instanceRepository;

	public InstanceService(InstanceRepository instanceRepository) {
		this.instanceRepository = instanceRepository;
	}

	public List<Instance> findByImageId(Integer id) {
		return instanceRepository.findByImageId(id);
	}

	public void insert(Instance instance) {
		instanceRepository.save(instance);
	}

	public Instance findById(Integer id) {
		if (instanceRepository.findById(id).isEmpty())
			return null;
		return instanceRepository.findById(id).get();
	}

	public List<Instance> findAll() {
		return StreamSupport.stream(instanceRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public void update(Instance instance) {
		instanceRepository.save(instance);
	}
	
	public void delete(Integer id) {
		instanceRepository.deleteById(id);
	}

}
