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

import com.backend.entities.terraform.terraformDatabase.Instance;
import com.backend.service.InstanceService;

@Controller
@RequestMapping("/instance")
public class InstanceController {

	private InstanceService instanceService;

	public InstanceController(InstanceService instanceService) {
		this.instanceService = instanceService;
	}

	@PostMapping("/insert")
	public @ResponseBody int add(@RequestBody Map<String, String> instanceData) {
		Instance newInstance = new Instance();
		newInstance.setName(instanceData.get("name"));
		if (!checkUpdateParams(instanceData.get("vCpus")))
			return Response.SC_NOT_ACCEPTABLE;
		newInstance.setvCpu(Integer.valueOf(instanceData.get("vCpus")));
		if (!checkUpdateParams(instanceData.get("memory")))
			return Response.SC_NOT_ACCEPTABLE;
		newInstance.setMemory(Double.valueOf(instanceData.get("memory")));
		if (!checkUpdateParams(instanceData.get("cost")))
			return Response.SC_NOT_ACCEPTABLE;
		newInstance.setCost(Double.valueOf(instanceData.get("cost")));
		if (!checkUpdateParams(instanceData.get("image_id")))
			return Response.SC_NOT_ACCEPTABLE;
		newInstance.setImagen_id(Integer.valueOf(instanceData.get("image_id")));
		instanceService.insert(newInstance);

		return Response.SC_OK;
	}

	@PostMapping("/delete")
	public @ResponseBody int delete(@RequestParam Integer id) {
		if (instanceService.findById(id) == null)
			return Response.SC_NOT_FOUND;
		instanceService.delete(id);
		return Response.SC_OK;
	}

	@GetMapping("/list")
	public @ResponseBody List<Instance> listAll() {
		return instanceService.findAll();
	}

	@PostMapping("/update")
	public @ResponseBody int update(@RequestBody Map<String, String> instanceData) {
		Instance newInstance = instanceService.findById(Integer.valueOf(instanceData.get("id")));
		if (newInstance == null)
			return Response.SC_NOT_FOUND;
		if (checkUpdateParams(instanceData.get("name"))) {
			newInstance.setName(instanceData.get("name"));
		}
		if (checkUpdateParams(instanceData.get("vCpu"))) {
			newInstance.setvCpu(Integer.valueOf(instanceData.get("vCpu")));
		}
		if (checkUpdateParams(instanceData.get("memory"))) {
			newInstance.setMemory(Double.valueOf(instanceData.get("memory")));
		}
		if (checkUpdateParams(instanceData.get("cost"))) {
			newInstance.setCost(Double.valueOf(instanceData.get("cost")));
		}

		instanceService.update(newInstance);
		return Response.SC_OK;
	}

	@GetMapping("/findById")
	public @ResponseBody Instance findById(@RequestParam Integer id) {
		if (instanceService.findById(id) == null)
			return null;
		return instanceService.findById(id);
	}

	@GetMapping("/fromImage")
	public @ResponseBody List<Instance> findByImageId(@RequestParam Integer image_id) {
		return instanceService.findByImageId(image_id);
	}

	private boolean checkUpdateParams(String param) {
		if (param == null || param.isEmpty() || param.isBlank())
			return false;
		return true;

	}

}
