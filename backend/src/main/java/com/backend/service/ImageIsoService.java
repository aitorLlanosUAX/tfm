package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.repository.ImageIsoRepository;

@Service
public class ImageIsoService {

	ImageIsoRepository imageRepository;
	InstanceService instanceService;

	public ImageIsoService(ImageIsoRepository imageRepository, InstanceService instanceService) {
		this.imageRepository = imageRepository;
		this.instanceService = instanceService;
	}

	public List<ImageIso> findByRegionIdAndProviderId(Integer regionId, Integer providerId) {
		return imageRepository.findByRegionIdAndProviderId(regionId,providerId);
	}

	public void insert(ImageIso imageIso) {
		imageRepository.save(imageIso);
	}

	public void update(ImageIso imageIso) {
		imageRepository.save(imageIso);
	}

	public ImageIso findById(Integer id) {
		if (imageRepository.findById(id).isEmpty())
			return null;
		return imageRepository.findById(id).get();
	}

	public List<ImageIso> findAll() {
		return StreamSupport.stream(imageRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	public void delete(Integer id) {
		instanceService.findByImageId(id).forEach(instance -> instanceService.delete(instance.getId()));
		imageRepository.deleteById(id);
	}

}
