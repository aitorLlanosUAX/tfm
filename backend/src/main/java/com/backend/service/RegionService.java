package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.repository.RegionRepository;;

@Service
public class RegionService {

	RegionRepository regionRepository;
	ImageIsoService imageService;

	public RegionService(RegionRepository regionRepository, ImageIsoService imageIsoService) {
		this.regionRepository = regionRepository;
		this.imageService = imageIsoService;
	}

	public List<Region> findByProviderId(Integer provider_id) {
		return regionRepository.findByProviderId(provider_id);
	}

	public void insert(Region region) {
		regionRepository.save(region);
	}

	public Region findById(Integer id) {
		if (regionRepository.findById(id).isEmpty())
			return null;
		return regionRepository.findById(id).get();
	}

	public List<Region> findAll() {
		return StreamSupport.stream(regionRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public void update(Region region) {
		regionRepository.save(region);
	}

	public void delete(Region region) {
		imageService.findByRegionIdAndProviderId(region.getId(), region.getProvider_id())
				.forEach(image -> imageService.delete(image.getId()));
		regionRepository.delete(region);
	}

}
