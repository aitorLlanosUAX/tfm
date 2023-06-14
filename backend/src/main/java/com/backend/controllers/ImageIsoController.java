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

import com.backend.entities.terraform.terraformDatabase.ImageIso;
import com.backend.service.ImageIsoService;

@Controller
@RequestMapping("/image")
public class ImageIsoController {

	private ImageIsoService imageIsoService;

	public ImageIsoController(ImageIsoService imageIsoService) {
		this.imageIsoService = imageIsoService;
	}

	@PostMapping("/insert")
	public @ResponseBody int add(@RequestBody Map<String, String> imageData) {
		ImageIso imageIso = new ImageIso();
		if (!checkUpdateParams(imageData.get("name")))
			return Response.SC_BAD_REQUEST;
		imageIso.setName(imageData.get("name"));
		imageIso.setOperatingSystem(imageData.get("operatingSystem"));
		if (!checkUpdateParams(imageData.get("region_id")))
			return Response.SC_BAD_REQUEST;
		if (!checkUpdateParams(imageData.get("provider_id")))
			return Response.SC_BAD_REQUEST;
		imageIso.setRegion_id(Integer.valueOf(imageData.get("region_id")));
		imageIso.setProvider_id(Integer.valueOf(imageData.get("provider_id")));
		imageIsoService.insert(imageIso);
		return Response.SC_OK;
	}

	@PostMapping("/delete")
	public @ResponseBody int delete(@RequestParam Integer id) {
		if (imageIsoService.findById(id) == null)
			return Response.SC_NOT_FOUND;
		imageIsoService.delete(id);
		return Response.SC_OK;
	}

	@GetMapping("/list")
	public @ResponseBody List<ImageIso> listAll() {
		return imageIsoService.findAll();
	}

	@PostMapping("/update")
	public @ResponseBody int update(@RequestBody Map<String, String> imageData) {
		ImageIso imageIso = imageIsoService.findById(Integer.valueOf(imageData.get("id")));
		if (imageIso == null)
			return Response.SC_NOT_FOUND;
		if (checkUpdateParams(imageData.get("name")))
			imageIso.setName(imageData.get("name"));
		if (checkUpdateParams(imageData.get("operatingSystem")))
			imageIso.setOperatingSystem(imageData.get("operatingSystem"));
		imageIsoService.update(imageIso);
		return Response.SC_OK;
	}

	@GetMapping("/findById")
	public @ResponseBody ImageIso findById(@RequestParam Integer id) {
		if (imageIsoService.findById(id) == null)
			return null;
		return imageIsoService.findById(id);
	}

	@GetMapping("/findByRegionAndProvider")
	public @ResponseBody List<ImageIso> findByRegionId(@RequestParam Integer region_id,
			@RequestParam Integer provider_id) {
		return imageIsoService.findByRegionIdAndProviderId(region_id, provider_id);
	}

	private boolean checkUpdateParams(String param) {
		if (param == null || param.isEmpty() || param.isBlank())
			return false;
		return true;

	}

}
