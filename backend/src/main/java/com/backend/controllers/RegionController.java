package com.backend.controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.entities.terraform.terraformDatabase.Region;
import com.backend.service.RegionService;

@Controller
@RequestMapping("/region")
public class RegionController {

	private RegionService regionService;

	public RegionController(RegionService regionService) {
		this.regionService = regionService;
	}

	@PostMapping("/insert")
	public @ResponseBody int add(@RequestBody Map<String, String> regionData) {
		Region toInsert = new Region();
		toInsert.setZone(regionData.get("zone"));
		toInsert.setName(regionData.get("name"));

		if (!checkParams(regionData.get("zone")))
			return Response.SC_BAD_REQUEST;
		if (!checkParams(regionData.get("name")))
			return Response.SC_BAD_REQUEST;
		if (!checkParams(regionData.get("provider_id")))
			return Response.SC_BAD_REQUEST;
		toInsert.setProvider_id(Integer.valueOf(regionData.get("provider_id")));

		regionService.insert(toInsert);
		return Response.SC_OK;
	}

	@PostMapping("/delete")
	public @ResponseBody int delete(@RequestParam Integer id) {
		if (regionService.findById(id) == null)
			return Response.SC_NOT_FOUND;
		regionService.delete(regionService.findById(id));
		return Response.SC_OK;
	}

	@GetMapping("/list")
	public @ResponseBody List<Region> listAll() {
		return regionService.findAll();
	}

	@PostMapping("/update")
	public @ResponseBody int update(@RequestBody Map<String, String> regionData) {
		Region updater = regionService.findById(Integer.valueOf(regionData.get("id")));
		if (updater == null)
			return Response.SC_NOT_FOUND;
		updater.setZone(regionData.get("zone"));
		updater.setName(regionData.get("name"));
		regionService.update(updater);
		return Response.SC_OK;
	}

	@GetMapping("/findById")
	public @ResponseBody Region findById(@RequestParam Integer id) {
		if (regionService.findById(id) == null)
			return null;
		return regionService.findById(id);
	}

	@GetMapping("/fromProvider")
	public @ResponseBody List<Region> findByProviderId(@RequestParam Integer provider_id) {
		return regionService.findByProviderId(provider_id);
	}

	private boolean checkParams(String param) {
		if (param == null || param.isEmpty() || param.isBlank())
			return false;
		return true;

	}

}
